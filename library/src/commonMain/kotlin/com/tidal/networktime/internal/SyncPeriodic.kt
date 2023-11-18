package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import kotlinx.coroutines.delay
import kotlin.time.Duration

internal class SyncPeriodic(
  private val ntpServers: Iterable<NTPServer>,
  private val syncInterval: Duration,
  private val referenceClock: KotlinXDateTimeSystemClock,
  private val synchronizationResultProcessor: SynchronizationResultProcessor,
  private val ntpExchanger: NTPExchanger = NTPExchanger(
    referenceClock,
    NTPPacketSerializer(),
    NTPPacketDeserializer(),
  ),
) {
  suspend operator fun invoke() {
    while (true) {
      SyncSingular(ntpServers, ntpExchanger, referenceClock, synchronizationResultProcessor)()
      delay(syncInterval)
    }
  }
}
