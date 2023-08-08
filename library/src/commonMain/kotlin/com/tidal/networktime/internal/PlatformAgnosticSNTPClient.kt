package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import com.tidal.networktime.ReadableClock
import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal class PlatformAgnosticSNTPClient(
  val ntpServers: Array<out NTPServer>,
  val referenceClock: ReadableClock,
  val coroutineScope: CoroutineScope,
  val synchronizationInterval: Duration = 64.seconds,
  httpClientFactory: HttpClientFactory = HttpClientFactory(),
) {
  val synchronizedEpochTime: Duration?
    get() = TODO("Get the time")
  private val httpClient = httpClientFactory()

  fun startSynchronization(): Unit = TODO("Start or return")

  fun stopSynchronization(): Unit = TODO("Stop or return")
}
