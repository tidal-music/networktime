package com.tidal.networktime.internal

import com.tidal.networktime.AddressFamily
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress

internal actual class AddressResolver {
  actual operator fun invoke(
    address: String,
    addressFamilies: Array<out AddressFamily>
  ): Iterable<String> = InetAddress.getAllByName(address)
    .mapNotNull {
      when (it) {
        is Inet4Address -> it.takeIf { addressFamilies.contains(AddressFamily.INET) }
        is Inet6Address -> it.takeIf { addressFamilies.contains(AddressFamily.INET6) }
        else -> throw IllegalArgumentException(
          "Illegal InetAddress with type ${it.javaClass.simpleName}"
        )
      }?.hostAddress
    }
}
