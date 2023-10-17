package com.tidal.networktime.internal

import com.tidal.networktime.DNSLookupStrategy
import com.tidal.networktime.NTPServer

internal class SyncSingular(
  private val domainNameResolver: DomainNameResolver,
  private val ntpServers: Iterable<NTPServer>,
) {
  suspend operator fun invoke() {
    // TODO Make iterations of this forEach parallel with each other
    ntpServers.forEach { ntpServer ->
      domainNameResolver(
        ntpServer.name,
        when (ntpServer.dnsLookupStrategy) {
          DNSLookupStrategy.IP_V4 -> listOf(DnsResourceRecord.A)
          DNSLookupStrategy.IP_V6 -> listOf(DnsResourceRecord.AAAA)
          DNSLookupStrategy.ALL -> listOf(DnsResourceRecord.A, DnsResourceRecord.AAAA)
        },
      )
      // TODO Take the addresses and continue with the protocol
    }
  }
}
