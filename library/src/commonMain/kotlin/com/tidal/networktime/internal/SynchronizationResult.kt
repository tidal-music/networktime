package com.tidal.networktime.internal

import kotlin.time.Duration

internal data class SynchronizationResult(
  val synchronizedEpochTime: Duration,
  val synchronizedAtReferenceEpochTime: Duration,
)
