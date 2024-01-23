package com.tidal.networktime.internal

import kotlinx.coroutines.Job
import kotlin.concurrent.Volatile

internal class MutableState(
  var job: Job? = null,
  @Volatile
  var synchronizationResult: SynchronizationResult? = null,
)
