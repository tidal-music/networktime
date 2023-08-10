package com.tidal.networktime.internal

internal class StopSyncRunnable(private val mutableState: MutableState) : () -> Unit {
  override operator fun invoke() = mutableState.run {
    job?.cancel() ?: return
    job = null
  }
}
