package com.tidal.networktime.internal

import kotlin.time.Duration

internal class NtpExchanger(
  private val referenceClock: KotlinXDateTimeSystemClock,
  private val fromEpochNtpTimestampFactory: FromEpochNtpTimestampFactory,
  private val ntpPacketSerializer: NtpPacketSerializer,
  private val ntpPacketDeserializer: NtpPacketDeserializer,
) {
  operator fun invoke(
    address: String,
    queryTimeout: Duration,
    ntpVersion: UByte,
  ): NtpExchangeResult? {
    val ntpUdpSocketOperations = NtpUdpSocketOperations()
    return try {
      ntpUdpSocketOperations.prepareSocket(queryTimeout.inWholeMilliseconds)
      val ntpPacket = NtpPacket(versionNumber = ntpVersion.toInt(), mode = NTP_MODE_CLIENT)
      val requestTime = referenceClock.referenceEpochTime
      ntpPacket.transmitEpochTimestamp = fromEpochNtpTimestampFactory(requestTime)
      val buffer = ntpPacketSerializer(ntpPacket)
      ntpUdpSocketOperations.exchangePacketInPlace(
        buffer,
        address,
        NTP_PORT_NUMBER,
      )
      val returnTime = referenceClock.referenceEpochTime
      ntpPacketDeserializer(buffer)?.let { NtpExchangeResult(returnTime, it) }
    } catch (_: Throwable) {
      null
    } finally {
      ntpUdpSocketOperations.closeSocket()
    }
  }

  companion object {
    private const val NTP_MODE_CLIENT = 3
    private const val NTP_PORT_NUMBER = 123
  }
}
