package com.tidal.networktime.internal

import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

internal data class NtpPacket(
  val leapIndicator: Byte = 0,
  val versionNumber: Byte = 0,
  val mode: Byte = 0,
  val stratum: Byte = 0,
  val poll: Byte = 0,
  val precision: Byte = 0,
  val rootDelay: Duration = Duration.ZERO,
  val rootDispersion: Duration = Duration.ZERO,
  val referenceIdentifier: Int = 0,
  val referenceEpochTimestamp: Duration = Duration.ZERO,
  val originateEpochTimestamp: Duration = Duration.ZERO,
  val receiveEpochTimestamp: Duration = Duration.ZERO,
  val transmitEpochTimestamp: Duration = Duration.ZERO,
) {
  init {
    // Check sizes of fields whose type does not match their corresponding size in the actual packet
    check(leapIndicator <= 0b0011)
    check(versionNumber <= 0b0111)
    check(mode <= 0b0111)
  }

  companion object {
    val NTP_EPOCH_OFFSET_WITH_EPOCH = (365.days * 70 + 17.days)
  }
}
