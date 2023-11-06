package com.tidal.networktime.internal

import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

internal class NtpPacketDeserializer {
  operator fun invoke(bytes: ByteArray): NtpPacket? {
    var index = 0
    val leapIndicator = (bytes[index].toInt() shr 6) and 0b11
    if (leapIndicator == LEAP_INDICATOR_CLOCK_UNSYNCHRONIZED) {
      return null
    }
    val versionNumber = (bytes[index].toInt() shr 3) and 0b111
    val mode = bytes[index].toInt() and 0b111
    if (mode != MODE_SERVER) {
      return null
    }
    ++index
    val stratum = bytes[index++].asUnsignedInt
    if (stratum >= STRATUM_CLOCK_NOT_SYNCHRONIZED) {
      return null
    }
    val poll = bytes[index++].asSignedIntToThePowerOf2.seconds
    val precision = bytes[index++].asSignedIntToThePowerOf2.milliseconds
    val rootDelay = bytes.sliceArray(index until index + 4).asNtpIntervalToInterval
    index += 4
    val rootDispersion = bytes.sliceArray(index until index + 4).asNtpIntervalToInterval
    index += 4
    val referenceIdentifier = bytes.sliceArray(index until index + 4).decodeToString()
    index += 4
    val reference = bytes.sliceArray(index until index + 8).asNtpTimestamp
    index += 8
    val originate = bytes.sliceArray(index until index + 8).asNtpTimestamp
    index += 8
    val receive = bytes.sliceArray(index until index + 8).asNtpTimestamp
    index += 8
    val transmit = bytes.sliceArray(index until index + 8).asNtpTimestamp
    return NtpPacket(
      leapIndicator,
      versionNumber,
      mode,
      stratum,
      poll,
      precision,
      rootDelay,
      rootDispersion,
      referenceIdentifier,
      reference,
      originate,
      receive,
      transmit,
    )
  }

  private val Byte.asSignedIntToThePowerOf2
    get() = 2.toDouble().pow(toInt())

  private val Byte.asUnsignedInt: Int
    get() = toUByte().toInt()

  private val ByteArray.asNtpIntervalToInterval: Duration
    get() {
      var index = 0
      val seconds = (this[index++].asUnsignedInt shl 8) + this[index++].asUnsignedInt
      val fraction = ((this[index++].asUnsignedInt shl 8) + this[index].asUnsignedInt)
        .toDouble() / (1 shl 16) * 1_000
      return seconds.seconds + fraction.milliseconds
    }

  private val Byte.asUnsignedLong: Long
    get() = toUByte().toLong()

  private val ByteArray.asNtpTimestamp: NtpTimestamp
    get() {
      var index = 0
      val ntpMillis = (this[index++].asUnsignedLong shl 56) or
        (this[index++].asUnsignedLong shl 48) or
        (this[index++].asUnsignedLong shl 40) or
        (this[index++].asUnsignedLong shl 32) or
        (this[index++].asUnsignedLong shl 24) or
        (this[index++].asUnsignedLong shl 16) or
        (this[index++].asUnsignedLong shl 8) or
        this[index].asUnsignedLong
      return NtpTimestamp(ntpMillis.milliseconds)
    }

  companion object {
    private const val LEAP_INDICATOR_CLOCK_UNSYNCHRONIZED = 0b11
    private const val MODE_SERVER = 4
    private const val STRATUM_CLOCK_NOT_SYNCHRONIZED = 16
  }
}
