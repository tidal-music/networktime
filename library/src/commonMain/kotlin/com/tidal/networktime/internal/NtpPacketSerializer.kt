package com.tidal.networktime.internal

import kotlin.time.Duration

internal class NtpPacketSerializer {
  operator fun invoke(ntpPacket: NtpPacket) = with(ntpPacket) {
    byteArrayOf(
      ((leapIndicator.toInt() and 0b11) or (versionNumber.toInt() and 0b110)).toByte(),
      ((versionNumber.toInt() and 0b1) or (mode.toInt() and 0b111)).toByte(),
      stratum,
      poll,
      precision,
      *rootDelay.asIntervalToNtpInterval,
      *rootDispersion.asIntervalToNtpInterval,
      (referenceIdentifier shr 24).toByte(),
      (referenceIdentifier shr 16).toByte(),
      (referenceIdentifier shr 8).toByte(),
      referenceIdentifier.toByte(),
      *referenceEpochTimestamp.asEpochTimestampToNtpEpochTimestamp,
      *originateEpochTimestamp.asEpochTimestampToNtpEpochTimestamp,
      *receiveEpochTimestamp.asEpochTimestampToNtpEpochTimestamp,
      *transmitEpochTimestamp.asEpochTimestampToNtpEpochTimestamp,
    )
  }

  private val Duration.asIntervalToNtpInterval: ByteArray
    get() {
      val wholeSeconds = inWholeSeconds
      val fraction = (inWholeMilliseconds - wholeSeconds * 1_000) * (1 shl 16) / 1_000
      return byteArrayOf(
        (wholeSeconds shr 8).toByte(),
        wholeSeconds.toByte(),
        (fraction shr 8).toByte(),
        fraction.toByte(),
      )
    }

  private val Duration.asEpochTimestampToNtpEpochTimestamp: ByteArray
    get() {
      val seconds = (this + NtpPacket.NTP_EPOCH_OFFSET_WITH_EPOCH).inWholeSeconds
      val fraction = (inWholeMilliseconds - inWholeSeconds * 1_000) *
        0b100000000000000000000000000000000 /
        1_000
      return byteArrayOf(
        (seconds shr 24).toByte(),
        (seconds shr 16).toByte(),
        (seconds shr 8).toByte(),
        seconds.toByte(),
        (fraction shr 24).toByte(),
        (fraction shr 16).toByte(),
        (fraction shr 8).toByte(),
        fraction.toByte(),
      )
    }
}
