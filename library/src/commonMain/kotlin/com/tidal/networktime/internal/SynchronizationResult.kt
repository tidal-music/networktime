package com.tidal.networktime.internal

import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
internal data class SynchronizationResult(
  val synchronizedEpochTime: Duration,
  val synchronizedAtReferenceEpochTime: Duration,
)
