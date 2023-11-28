package com.tidal.networktime.internal

import kotlin.time.Duration

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class NameResolver() {
  suspend operator fun invoke(
    name: String,
    timeout: Duration,
    includeIPv4: Boolean,
    includeIPv6: Boolean,
  ): Iterable<String>
}
