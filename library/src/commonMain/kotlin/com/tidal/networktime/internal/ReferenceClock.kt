package com.tidal.networktime.internal

import kotlin.time.Duration

internal interface ReferenceClock {
  val referenceEpochTime: Duration
}
