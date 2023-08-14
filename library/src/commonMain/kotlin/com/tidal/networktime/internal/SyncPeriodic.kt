package com.tidal.networktime.internal

import kotlinx.coroutines.delay
import kotlin.time.Duration

internal class SyncPeriodic(private val syncInterval: Duration) {
  suspend operator fun invoke() {
    while (true) {
      SyncSingular()()
      delay(syncInterval)
    }
  }
}
