package com.tidal.networktime.internal

import kotlin.time.Duration

data class SynchronizationResult(
  val synchronizedEpochTime: Duration,
  val synchronizedAtReferenceEpochTime: Duration,
)
