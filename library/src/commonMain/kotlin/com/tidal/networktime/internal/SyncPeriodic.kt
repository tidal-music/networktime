package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import kotlinx.coroutines.delay
import kotlin.time.Duration

internal class SyncPeriodic(
  private val domainNameResolver: DomainNameResolver,
  private val ntpServers: Iterable<NTPServer>,
  private val syncInterval: Duration,
) {
  suspend operator fun invoke() {
    while (true) {
      SyncSingular(domainNameResolver, ntpServers)()
      delay(syncInterval)
    }
  }
}
