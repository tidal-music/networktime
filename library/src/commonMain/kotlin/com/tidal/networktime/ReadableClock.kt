package com.tidal.networktime

import kotlin.time.Duration

interface ReadableClock {
  val epochTime: Duration
}
