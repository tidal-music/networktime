package com.tidal.networktime

import kotlin.time.Duration

interface WriteableClock : ReadableClock {
  override var time: Duration
}
