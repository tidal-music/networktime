package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import com.tidal.networktime.ReadableClock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal class SNTPClientImpl(
  ntpServers: Array<out NTPServer>,
  private val referenceClock: ReadableClock,
  coroutineScope: CoroutineScope,
  syncInterval: Duration = 64.seconds,
  private val mutableState: MutableState = MutableState(),
  private val operationCoordinator: OperationCoordinator =
    OperationCoordinator(
      mutableState,
      coroutineScope,
      Dispatchers.IO,
      syncInterval,
      ntpServers.asIterable(),
      referenceClock,
    ),
) {
  val epochTime: Duration?
    get() {
      val (synchronizedTime, synchronizedAt) = mutableState.synchronizationResult ?: return null
      return synchronizedTime - synchronizedAt + referenceClock.epochTime
    }

  fun enableSynchronization() = operationCoordinator.dispatchStartSync()

  fun disableSynchronization() = operationCoordinator.dispatchStopSync()
}
