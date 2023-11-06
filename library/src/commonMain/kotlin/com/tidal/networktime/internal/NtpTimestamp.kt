package com.tidal.networktime.internal

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * NTP timestamps have more precision than epochs represented with Kotlin's Long, so use them as the
 * non-computed property.
 */
// TODO Convert this and FromEpochNtpTimestampFactory into two inline classes wrapping Duration
internal data class NtpTimestamp(val ntpTime: Duration) {
  val epochTime: Duration
    get() {
      val ntpTimeValue = ntpTime.inWholeMilliseconds
      val seconds = ntpTimeValue ushr 32 and 0xffffffff
      val fraction = (1000.0 * (ntpTimeValue and 0xffffffff) / 0x100000000).toLong()
      val mostSignificantBit = seconds and 0x80000000L
      return (
        if (mostSignificantBit == 0L) {
          NtpPacket.NTP_TIMESTAMP_BASE_WITH_EPOCH_MSB_0_MILLISECONDS
        } else {
          NtpPacket.NTP_TIMESTAMP_BASE_WITH_EPOCH_MSB_1_MILLISECONDS
        } + seconds * 1000 + fraction
        ).milliseconds
    }
}
