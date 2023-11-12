package com.tidal.networktime.internal

import com.tidal.networktime.AddressFamily
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress

internal actual class AddressResolver {
  actual operator fun invoke(
    address: String,
    addressFamilies: Array<out AddressFamily>,
  ): Iterable<String> {
    val containsInet = addressFamilies.contains(AddressFamily.INET)
    val containsInet6 = addressFamilies.contains(AddressFamily.INET6)
    return InetAddress.getAllByName(address)
      .mapNotNull {
        when (it) {
          is Inet4Address -> it.takeIf { containsInet }
          is Inet6Address -> it.takeIf { containsInet6 }
          else -> throw IllegalArgumentException(
            "Illegal InetAddress with type ${it.javaClass.simpleName}",
          )
        }?.hostAddress
      }
  }
}
