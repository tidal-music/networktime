package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import com.tidal.networktime.NTPVersion
import com.tidal.networktime.ProtocolFamily
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

internal class SyncSingular(
  private val ntpServers: Iterable<NTPServer>,
  private val ntpExchangeCoordinator: NTPExchangeCoordinator,
  private val referenceClock: KotlinXDateTimeSystemClock,
  private val synchronizationResultProcessor: SynchronizationResultProcessor,
  private val hostNameResolver: HostNameResolver = HostNameResolver(),
) {
  suspend operator fun invoke() {
    val selectedResult = ntpServers.map {
      withContext(currentCoroutineContext()) {
        async { pickNTPPacketWithShortestRoundTrip(it) }
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
    synchronizationResultProcessor.synchronizationResult = SynchronizationResult(
      selectedResult.run { returnTime + clockOffset },
      referenceClock.referenceEpochTime,
    )
  }

  private suspend fun pickNTPPacketWithShortestRoundTrip(ntpServer: NTPServer) = with(ntpServer) {
    try {
      hostNameResolver(
        hostName,
        dnsResolutionTimeout,
        ProtocolFamily.INET in protocolFamilies,
        ProtocolFamily.INET6 in protocolFamilies,
      ).map { resolvedName ->
        (1..queriesPerResolvedAddress).mapNotNull {
          val ret = ntpExchangeCoordinator(
            resolvedName,
            queryConnectTimeout,
            queryReadTimeout,
            when (ntpVersion) {
              NTPVersion.ZERO -> 0U
              NTPVersion.ONE -> 1U
              NTPVersion.TWO -> 2U
              NTPVersion.THREE -> 3U
              NTPVersion.FOUR -> 4U
            },
          )
          if (it != queriesPerResolvedAddress) {
            delay(waitBetweenResolvedAddressQueries)
          }
          if (
            ret?.ntpPacket?.run { rootDelay <= maxRootDelay && rootDispersion <= maxRootDispersion }
            == true
          ) {
            ret
          } else {
            null
          }
        }
          .takeIf { it.isNotEmpty() }
          ?.minBy { it.roundTripDelay }
      }
    } catch (_: Throwable) {
      emptySet()
    }
  }
}
