package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import com.tidal.networktime.ReadableClock
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal class PlatformAgnosticSNTPClient(
  private val ntpServers: Array<out NTPServer>,
  private val referenceClock: ReadableClock,
  private val coroutineScope: CoroutineScope,
  private val syncInterval: Duration = 64.seconds,
  private val httpClient: HttpClient = HttpClientFactory()(),
  private val operationCoordinator: OperationCoordinator =
    OperationCoordinator(MutableState(), coroutineScope, Dispatchers.IO, syncInterval),
) {
  val synchronizedEpochTime: Duration?
    get() = TODO("Get the time")

  fun enableSynchronization() = operationCoordinator.dispatchStartSync()

  fun disableSynchronization() = operationCoordinator.dispatchStopSync()
}
