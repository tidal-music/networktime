package com.tidal.networktime.internal

internal actual class AddressResolver {
  actual operator fun invoke(
    address: String,
    includeIPv4: Boolean,
    includeIPv6: Boolean,
  ): Iterable<String> = emptyList() // TODO
}
