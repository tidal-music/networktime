package com.tidal.networktime.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext
import kotlin.time.Duration

internal class DomainNameResolver(
  private val httpClient: HttpClient,
  private val dnsOverHttpsResponseParser: DnsOverHttpsResponseParser,
) {
  suspend operator fun invoke(
    name: String,
    dnsResourceRecords: Iterable<DnsResourceRecord>,
    requestTimeout: Duration,
  ): Iterable<String> = dnsResourceRecords.map {
    withContext(currentCoroutineContext()) {
      async { invoke(name, it, requestTimeout) }
    }
  }.flatMap { it.await() }

  private tailrec suspend operator fun invoke(
    name: String,
    dnsResourceRecord: DnsResourceRecord,
    requestTimeout: Duration,
  ): Iterable<String> = with(
    httpClient.get("https://dns.google/resolve") {
      url {
        parameters.append("type", dnsResourceRecord.type)
        parameters.append("ct", "application/x-javascript")
        parameters.append("name", name)
      }
      timeout {
        requestTimeoutMillis = requestTimeout.inWholeMilliseconds
      }
    },
  ) {
    return when (status.value) {
      200 -> dnsOverHttpsResponseParser(body())
      // Requires using RFC8484 which is not currently supported
      in arrayOf(307, 308) -> emptySet()
      // Errors
      in arrayOf(400, 413, 414, 415, 429, 500, 501, 502) -> emptySet()
      else -> invoke(headers["Location"]!!, dnsResourceRecord, requestTimeout)
    }
  }
}
