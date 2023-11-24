package com.tidal.networktime.internal

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class AddressResolver() {
  suspend operator fun invoke(
    address: String,
    includeIPv4: Boolean,
    includeIPv6: Boolean
  ): Iterable<String>
}
