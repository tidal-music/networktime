package com.tidal.networktime.internal

import com.tidal.networktime.AddressFamily

internal expect class AddressResolver() {
  operator fun invoke(address: String, addressFamilies: Array<out AddressFamily>): Iterable<String>
}
