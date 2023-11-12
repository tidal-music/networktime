package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration

internal class SyncPeriodic(
  private val ntpServers: Iterable<NTPServer>,
  private val syncInterval: Duration,
  private val referenceClock: KotlinXDateTimeSystemClock,
  private val mutableState: MutableState,
  random: Random = Random.Default,
  private val ntpExchanger: NtpExchanger = NtpExchanger(
    referenceClock,
    NtpPacketSerializer(random),
    NtpPacketDeserializer(),
  ),
) {
  suspend operator fun invoke() {
    while (true) {
      SyncSingular(ntpServers, ntpExchanger, referenceClock, mutableState)()
      delay(syncInterval)
    }
  }
}
