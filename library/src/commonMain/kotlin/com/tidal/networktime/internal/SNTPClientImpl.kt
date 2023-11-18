package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import okio.Path
import kotlin.time.Duration

internal class SNTPClientImpl(
  ntpServers: Array<out NTPServer>,
  coroutineScope: CoroutineScope,
  backupFilePath: Path?,
  syncInterval: Duration,
  private val referenceClock: KotlinXDateTimeSystemClock = KotlinXDateTimeSystemClock(),
  private val mutableState: MutableState = MutableState(),
  private val synchronizationResultProcessor: SynchronizationResultProcessor =
    SynchronizationResultProcessor(
      mutableState,
      backupFilePath,
    ),
  private val operationCoordinator: OperationCoordinator =
    OperationCoordinator(
      mutableState,
      synchronizationResultProcessor,
      coroutineScope,
      Dispatchers.IO,
      syncInterval,
      ntpServers.asIterable(),
      referenceClock,
    ),
) {
  val epochTime: Duration?
    get() {
      val (synchronizedTime, synchronizedAt) =
        synchronizationResultProcessor.synchronizationResult ?: return null
      return synchronizedTime - synchronizedAt + referenceClock.referenceEpochTime
    }

  fun enableSynchronization() = operationCoordinator.dispatchStartSync()

  fun disableSynchronization() = operationCoordinator.dispatchStopSync()
}
