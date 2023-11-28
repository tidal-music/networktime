package com.tidal.networktime.internal

import kotlinx.coroutines.withTimeoutOrNull
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress
import kotlin.time.Duration

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class NameResolver {
  actual suspend operator fun invoke(
    name: String,
    timeout: Duration,
    includeIPv4: Boolean,
    includeIPv6: Boolean,
  ): Iterable<String> = withTimeoutOrNull(timeout) {
    InetAddress.getAllByName(name)
  }?.mapNotNull {
    when (it) {
      is Inet4Address -> it.takeIf { includeIPv4 }
      is Inet6Address -> it.takeIf { includeIPv6 }
      else -> null
    }?.hostAddress
  } ?: emptySet()
}
