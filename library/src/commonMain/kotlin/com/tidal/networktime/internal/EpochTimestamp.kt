package com.tidal.networktime.internal

import kotlin.jvm.JvmInline
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@JvmInline
internal value class EpochTimestamp(val epochTime: Duration) {
  val asNTPTimestamp: NTPTimestamp
    get() {
      val millis = epochTime.inWholeMilliseconds
      val useBase1 = millis < NTPPacket.NTP_TIMESTAMP_BASE_WITH_EPOCH_MSB_0_MILLISECONDS
      val baseTimeMillis = millis -
        if (useBase1) {
          NTPPacket.NTP_TIMESTAMP_BASE_WITH_EPOCH_MSB_1_MILLISECONDS
        } else {
          NTPPacket.NTP_TIMESTAMP_BASE_WITH_EPOCH_MSB_0_MILLISECONDS
        }
      var seconds = baseTimeMillis / 1_000
      if (useBase1) {
        seconds = seconds or 0x80000000L
      }
      val fraction = baseTimeMillis % 1_000 * 0x100000000L / 1_000
      return NTPTimestamp((seconds shl 32 or fraction).milliseconds)
    }
}
