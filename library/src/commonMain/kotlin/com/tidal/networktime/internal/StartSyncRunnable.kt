package com.tidal.networktime.internal

internal class StartSyncRunnable(private val mutableState: MutableState) : () -> Unit {
  override operator fun invoke() = mutableState.run {
    if (job != null) {
      return
    }
    job = TODO()
  }
}
