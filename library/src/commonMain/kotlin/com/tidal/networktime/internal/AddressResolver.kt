package com.tidal.networktime.internal

internal expect class AddressResolver() {
  operator fun invoke(address: String, includeIPv4: Boolean, includeIPv6: Boolean): Iterable<String>
}
