package com.tidal.networktime.internal

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

internal class FromEpochNtpTimestampFactory {
  operator fun invoke(epochTime: Duration) = NtpTimestamp(epochTime.epochTimeAsNtpTime)

  private val Duration.epochTimeAsNtpTime: Duration
    get() {
      val millis = inWholeMilliseconds
      val useBase1 = millis < NtpPacket.NTP_TIMESTAMP_BASE_WITH_EPOCH_MSB_0_MILLISECONDS
      val baseTimeMillis = millis -
        if (useBase1) {
          NtpPacket.NTP_TIMESTAMP_BASE_WITH_EPOCH_MSB_1_MILLISECONDS
        } else {
          NtpPacket.NTP_TIMESTAMP_BASE_WITH_EPOCH_MSB_0_MILLISECONDS
        }
      var seconds = baseTimeMillis / 1_000
      if (useBase1) {
        seconds = seconds or 0x80000000L
      }
      val fraction = baseTimeMillis % 1_000 * 0x100000000L / 1_000
      return (seconds shl 32 or fraction).milliseconds
    }
}
