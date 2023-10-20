package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.time.Duration

internal class SyncEnable(
  private val mutableState: MutableState,
  private val coroutineScope: CoroutineScope,
  private val syncDispatcher: CoroutineDispatcher,
  private val syncInterval: Duration,
  private val ntpServers: Iterable<NTPServer>,
  private val referenceClock: ReferenceClock,
) : () -> Unit {
  override operator fun invoke() = with(mutableState) {
    val job = job
    if (job != null && !job.isCancelled) {
      return
    }
    this.job = coroutineScope.launch(syncDispatcher) {
      SyncPeriodic(ntpServers, syncInterval, referenceClock, mutableState)()
    }
  }
}
