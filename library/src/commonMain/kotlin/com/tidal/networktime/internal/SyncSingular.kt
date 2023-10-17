package com.tidal.networktime.internal

import com.tidal.networktime.DnsLookupStrategy
import com.tidal.networktime.NTPServer
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext

internal class SyncSingular(
  private val domainNameResolver: DomainNameResolver,
  private val ntpServers: Iterable<NTPServer>,
) {
  suspend operator fun invoke() {
    ntpServers.map {
      withContext(currentCoroutineContext()) {
        async { sync(it) }
      }
    }.onEach {
      it.await()
    }
  }

  private suspend fun sync(ntpServer: NTPServer) = with(ntpServer) {
    domainNameResolver(
      name,
      when (dnsLookupStrategy) {
        DnsLookupStrategy.IP_V4 -> listOf(DnsResourceRecord.A)
        DnsLookupStrategy.IP_V6 -> listOf(DnsResourceRecord.AAAA)
        DnsLookupStrategy.ALL -> listOf(DnsResourceRecord.A, DnsResourceRecord.AAAA)
      },
    )
    // TODO Take the addresses and continue with the protocol
  }
}
