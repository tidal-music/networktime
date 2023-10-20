package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.HttpTimeout.Plugin.install
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration

internal class SyncPeriodic(
  private val ntpServers: Iterable<NTPServer>,
  private val syncInterval: Duration,
  private val referenceClock: ReferenceClock,
  private val mutableState: MutableState,
  private val domainNameResolver: DomainNameResolver = DomainNameResolver(
    HttpClientFactory()() { install(HttpTimeout) },
    DnsOverHttpsResponseParser(),
  ),
  random: Random = Random.Default,
  private val ntpExchanger: NtpExchanger = NtpExchanger(
    referenceClock,
    NtpPacketSerializer(random),
    NtpPacketDeserializer(),
    random,
  ),
) {
  suspend operator fun invoke() {
    while (true) {
      SyncSingular(domainNameResolver, ntpServers, ntpExchanger, referenceClock, mutableState)()
      delay(syncInterval)
    }
  }
}
