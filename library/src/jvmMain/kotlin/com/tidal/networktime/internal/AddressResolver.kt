package com.tidal.networktime.internal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class AddressResolver {
  actual suspend operator fun invoke(
    address: String,
    includeIPv4: Boolean,
    includeIPv6: Boolean,
  ): Iterable<String> {
    return withContext(Dispatchers.IO) {
      InetAddress.getAllByName(address)
    }.mapNotNull {
      when (it) {
        is Inet4Address -> it.takeIf { includeIPv4 }
        is Inet6Address -> it.takeIf { includeIPv6 }
        else -> throw IllegalArgumentException(
          "Illegal InetAddress with type ${it.javaClass.simpleName}",
        )
      }?.hostAddress
    }
  }
}
