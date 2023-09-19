package com.tidal.networktime.internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext

internal class DomainNameResolver(
  private val httpClient: HttpClient,
  private val dnsOverHttpsResponseParser: DnsOverHttpsResponseParser,
) {

  suspend operator fun invoke(
    address: String,
    vararg dnsResourceRecords: String,
  ): Iterable<String> = dnsResourceRecords.map {
    withContext(currentCoroutineContext()) {
      async { invoke(address, it) }
    }
  }.flatMap { it.await() }

  private tailrec suspend operator fun invoke(
    address: String,
    dnsResourceRecord: String,
  ): Iterable<String> = with(
    httpClient.get("https://dns.google/resolve") {
      url {
        parameters.append("type", dnsResourceRecord)
        parameters.append("ct", "application/x-javascript")
        parameters.append("name", address)
      }
    },
  ) {
    return when (status.value) {
      200 -> dnsOverHttpsResponseParser(body())
      // Requires using RFC8484 which is not currently supported
      in arrayOf(307, 308) -> emptySet()
      // Errors
      in arrayOf(400, 413, 414, 415, 429, 500, 501, 502) -> emptySet()
      else -> invoke(headers["Location"]!!, dnsResourceRecord)
    }
  }

  companion object {
    // IPv4
    private const val DNS_RESOURCE_RECORD_A = "1"

    // IPv6
    const val DNS_RESOURCE_RECORD_AAAA = "28"
  }
}
