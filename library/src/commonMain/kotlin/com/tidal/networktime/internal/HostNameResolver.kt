package com.tidal.networktime.internal

import kotlin.time.Duration

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class HostNameResolver() {
  suspend operator fun invoke(
    hostName: String,
    timeout: Duration,
    includeINET: Boolean,
    includeINET6: Boolean,
  ): Iterable<String>
}
