package com.tidal.networktime.internal

import kotlin.time.Duration

internal class NtpPacketSerializer {
  operator fun invoke(ntpPacket: NtpPacket) = ntpPacket.run {
    ByteArray(48).apply {
      set(0, ((0 shl 6) or (versionNumber shl 3) or mode).toByte())
      transmitEpochTimestamp.ntpTime
        .ntpTimestampAsByteArray
        .forEachIndexed { i, it ->
          set(40 + i, it)
        }
    }
  }

  private val Duration.ntpTimestampAsByteArray: ByteArray
    get() = inWholeMilliseconds.run {
      byteArrayOf(
        (this shr 56 and 0xff).toByte(),
        (this shr 48 and 0xff).toByte(),
        (this shr 40 and 0xff).toByte(),
        (this shr 32 and 0xff).toByte(),
        (this shr 24 and 0xff).toByte(),
        (this shr 16 and 0xff).toByte(),
        (this shr 8 and 0xff).toByte(),
        (this and 0xff).toByte(),
      )
    }
}
