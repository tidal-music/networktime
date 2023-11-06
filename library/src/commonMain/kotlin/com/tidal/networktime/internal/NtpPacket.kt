package com.tidal.networktime.internal

import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

internal data class NtpPacket(
  val leapIndicator: Int = 0,
  val versionNumber: Int,
  val mode: Int,
  val stratum: Byte = 0,
  val poll: Byte = 0,
  val precision: Byte = 0,
  val rootDelay: Duration = Duration.INFINITE,
  val rootDispersion: Duration = Duration.INFINITE,
  val referenceIdentifier: Int = 0,
  val referenceEpochTimestamp: Duration = Duration.INFINITE,
  val originateEpochTimestamp: Duration = Duration.INFINITE,
  val receiveEpochTimestamp: Duration = Duration.INFINITE,
  val transmitEpochTimestamp: Duration = Duration.INFINITE,
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
