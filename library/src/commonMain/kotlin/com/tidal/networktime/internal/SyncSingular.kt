package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import com.tidal.networktime.NTPVersion
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

internal class SyncSingular(
  private val ntpServers: Iterable<NTPServer>,
  private val ntpExchanger: NtpExchanger,
  private val referenceClock: KotlinXDateTimeSystemClock,
  private val mutableState: MutableState,
  private val addressResolver: AddressResolver = AddressResolver(),
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
    addressResolver(name, addressFamilies).map { resolvedAddress ->
      (1..queriesPerResolvedAddress).mapNotNull {
        val ret = ntpExchanger(
          resolvedAddress,
          queryTimeout,
          when (ntpVersion) {
            NTPVersion.ZERO -> 0U
            NTPVersion.ONE -> 1U
            NTPVersion.TWO -> 2U
            NTPVersion.THREE -> 3U
            NTPVersion.FOUR -> 4U
          },
        )
        if (it.toShort() != queriesPerResolvedAddress) {
          delay(waitBetweenResolvedAddressQueries)
        }
        ret
      }
        .takeIf { it.isNotEmpty() }
        ?.minBy { it.roundTripDelay }
    }
  }
}
