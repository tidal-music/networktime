package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import okio.Path
import kotlin.time.Duration

internal class SNTPClientImpl
@OptIn(DelicateCoroutinesApi::class)
constructor(
  ntpServers: Array<out NTPServer>,
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
      GlobalScope,
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
