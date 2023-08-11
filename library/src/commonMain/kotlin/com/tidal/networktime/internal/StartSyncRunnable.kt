package com.tidal.networktime.internal

internal class StartSyncRunnable(private val mutableState: MutableState) : () -> Unit {
  override operator fun invoke() = with(mutableState) {
    val job = job
    if (job != null && !job.isCancelled) {
      return
    }
    this.job = TODO()
  }
}
