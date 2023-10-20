package com.tidal.networktime.internal

import com.tidal.networktime.DnsLookupStrategy
import com.tidal.networktime.NTPServer
import com.tidal.networktime.NTPVersion
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

internal class SyncSingular(
  private val domainNameResolver: DomainNameResolver,
  private val ntpServers: Iterable<NTPServer>,
  private val ntpExchanger: NtpExchanger,
  private val referenceClock: ReferenceClock,
  private val mutableState: MutableState,
) {
  suspend operator fun invoke() {
    val selectedResult = ntpServers.map {
      withContext(currentCoroutineContext()) {
        async { pickNtpPacketWithShortestRoundTrip(it) }
      }
    }.flatMap {
      it.await()
    }.filterNotNull()
      .sortedBy { it.clockOffset }
      .run {
        if (isEmpty()) {
          return
        } else {
          this[size / 2]
        }
      }
    mutableState.synchronizationResult = SynchronizationResult(
      selectedResult.run { timeMeasured + clockOffset },
      referenceClock.referenceEpochTime,
    )
  }

  private suspend fun pickNtpPacketWithShortestRoundTrip(ntpServer: NTPServer) = with(ntpServer) {
    domainNameResolver(
      name,
      when (dnsLookupStrategy) {
        DnsLookupStrategy.IP_V4 -> listOf(DnsResourceRecord.A)
        DnsLookupStrategy.IP_V6 -> listOf(DnsResourceRecord.AAAA)
        DnsLookupStrategy.ALL -> listOf(DnsResourceRecord.A, DnsResourceRecord.AAAA)
      },
      lookupTimeout,
    ).map { address ->
      (1..queriesPerResolvedAddress).mapNotNull {
        val ret = ntpExchanger(
          address,
          queryTimeout,
          when (ntpVersion) {
            NTPVersion.ZERO -> 0U
            NTPVersion.ONE -> 1U
            NTPVersion.TWO -> 2U
            NTPVersion.THREE -> 3U
            NTPVersion.FOUR -> 4U
          },
        )
        delay(waitBetweenResolvedAddressQueries)
        ret
      }
        .takeIf { it.isNotEmpty() }
        ?.minBy { it.roundTripDelay }
    }
  }
}
