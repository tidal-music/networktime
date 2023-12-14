package com.tidal.networktime.internal

import com.tidal.networktime.ProtocolFamily
import kotlinx.coroutines.withTimeoutOrNull
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress
import kotlin.time.Duration

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class HostNameResolver {
  actual suspend operator fun invoke(
    hostName: String,
    timeout: Duration,
    includeINET: Boolean,
    includeINET6: Boolean,
  ): Iterable<Pair<String, ProtocolFamily>> = withTimeoutOrNull(timeout) {
    InetAddress.getAllByName(hostName)
  }?.mapNotNull {
    val protocolFamily = when (it) {
      is Inet4Address -> ProtocolFamily.INET
      is Inet6Address -> ProtocolFamily.INET6
      else -> null
    }
    when {
      protocolFamily == ProtocolFamily.INET && includeINET ||
        protocolFamily == ProtocolFamily.INET6 && includeINET6 -> it.hostAddress to protocolFamily

      else -> null
    }
  } ?: emptySet()
}
