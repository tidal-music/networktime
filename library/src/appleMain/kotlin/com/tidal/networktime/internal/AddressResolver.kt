package com.tidal.networktime.internal

import com.tidal.networktime.AddressFamily

internal actual class AddressResolver {
  actual operator fun invoke(
    address: String,
    addressFamilies: Array<out AddressFamily>,
  ): Iterable<String> = emptyList() // TODO
}
