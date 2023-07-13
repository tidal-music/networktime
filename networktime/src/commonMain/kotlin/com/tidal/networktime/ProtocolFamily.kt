package com.tidal.networktime

import kotlin.native.ObjCName

/**
 * A designation of protocol families to discriminate resolved addresses on.
 */
@ObjCName(name = "TNTProtocolFamily", swiftName = "ProtocolFamily", exact = true)
enum class ProtocolFamily {
  /**
   * IPv4.
   */
  INET,

  /**
   * IPv6.
   */
  INET6,
}
