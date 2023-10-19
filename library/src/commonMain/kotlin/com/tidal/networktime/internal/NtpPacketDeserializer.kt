package com.tidal.networktime.internal

import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

internal class NtpPacketDeserializer {
  operator fun invoke(bytes: ByteArray): NtpPacket {
    var index = 0
    return NtpPacket(
      ((bytes[index++].toInt() shl 8) + bytes[index++]).toByte(),
      ((bytes[index++].toInt() shl 16) + (bytes[index++].toInt() shl 24) + bytes[index++]).toByte(),
      ((bytes[index++].toInt() shl 16) + (bytes[index++].toInt() shl 24) + bytes[index++]).toByte(),
      bytes[index++],
      bytes[index++],
      bytes[index++],
      bytes.sliceArray(index until index + 32).asNtpIntervalToInterval.also { index += 32 },
      bytes.sliceArray(index until index + 32).asNtpIntervalToInterval.also { index += 32 },
      (bytes[index++].toInt() shl 24) +
        (bytes[index++].toInt() shl 16) +
        (bytes[index++].toInt() shl 8) +
        bytes[index++].toInt(),
      bytes.sliceArray(index until index + 64).asNtpEpochTimestampToEpochTime
        .also { index += 64 },
      bytes.sliceArray(index until index + 64).asNtpEpochTimestampToEpochTime
        .also { index += 64 },
      bytes.sliceArray(index until index + 64).asNtpEpochTimestampToEpochTime
        .also { index += 64 },
      bytes.sliceArray(index until index + 64).asNtpEpochTimestampToEpochTime,
    )
  }

  private val ByteArray.asNtpIntervalToInterval: Duration
    get() {
      var index = 0
      val seconds = (this[index++].toUByte().toInt() shl 8) +
        this[index++].toUByte().toInt()
      val fraction = (
        (this[index++].toUByte().toInt() shl 8) +
          this[index].toUByte().toInt()
        ) *
        1_000 /
        (1 shl 16)
      return seconds.seconds + fraction.milliseconds
    }

  private val ByteArray.asNtpEpochTimestampToEpochTime: Duration
    get() {
      val rollOverAdjustment = if ((this[0].toInt() shr 7) == 0) {
        2.toDouble().pow(32).seconds
      } else {
        Duration.ZERO
      }
      var index = 0
      val seconds = (this[index++].toUByte().toInt() shl 24) +
        (this[index++].toUByte().toInt() shl 16) +
        (this[index++].toUByte().toInt() shl 8) +
        this[index++].toUByte().toInt()
      val fraction = (
        (this[index++].toUByte().toInt() shl 24) +
          (this[index++].toUByte().toInt() shl 16) +
          (this[index++].toUByte().toInt() shl 8) +
          this[index].toUByte().toInt()
        ) *
        1_000 /
        0b100000000000000000000000000000000
      return seconds.seconds +
        rollOverAdjustment -
        NtpPacket.NTP_EPOCH_OFFSET_WITH_EPOCH +
        fraction.milliseconds
    }
}
