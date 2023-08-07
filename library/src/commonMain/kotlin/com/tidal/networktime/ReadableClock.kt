package com.tidal.networktime

import kotlin.time.Duration

interface ReadableClock {
  val time: Duration
}
