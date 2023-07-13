package com.tidal.networktime

import kotlin.native.ObjCName

@ObjCName(name = "TNTNTPVersion", swiftName = "NTPVersion", exact = true)
enum class NTPVersion {
  ZERO,
  ONE,
  TWO,
  THREE,
  FOUR,
}
