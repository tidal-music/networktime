package com.tidal.networktime.internal

import kotlinx.coroutines.Job

internal class MutableState(
  var job: Job? = null,
  var synchronizationResult: SynchronizationResult? = null,
)
