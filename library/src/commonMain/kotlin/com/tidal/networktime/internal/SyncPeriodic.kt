package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import com.tidal.networktime.ReadableClock
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration

internal class SyncPeriodic(
  private val ntpServers: Iterable<NTPServer>,
  private val syncInterval: Duration,
  private val referenceClock: ReadableClock,
  private val mutableState: MutableState,
  private val domainNameResolver: DomainNameResolver = DomainNameResolver(
    HttpClientFactory()(),
    DnsOverHttpsResponseParser(),
  ),
  private val ntpExchanger: NtpExchanger = NtpExchanger(
    referenceClock,
    NtpPacketSerializer(),
    NtpPacketDeserializer(),
    Random.Default,
  ),
) {
  suspend operator fun invoke() {
    while (true) {
      SyncSingular(domainNameResolver, ntpServers, ntpExchanger, referenceClock, mutableState)()
      delay(syncInterval)
    }
  }
}
