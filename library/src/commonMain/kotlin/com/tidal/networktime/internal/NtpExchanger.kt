package com.tidal.networktime.internal

import kotlin.random.Random
import kotlin.time.Duration

internal class NtpExchanger(
  private val referenceClock: ReferenceClock,
  private val ntpPacketSerializer: NtpPacketSerializer,
  private val ntpPacketDeserializer: NtpPacketDeserializer,
  private val random: Random,
) {
  operator fun invoke(
    address: String,
    queryTimeout: Duration,
    ntpVersion: UInt,
  ): NtpExchangeResult? {
    val ntpUdpSocketOperations = NtpUdpSocketOperations()
    val requestPacket = NtpPacket(
      versionNumber = ntpVersion.toInt(),
      mode = NTP_MODE_CLIENT,
    )
    return try {
      ntpUdpSocketOperations.prepareSocket(queryTimeout.inWholeMilliseconds)
      val requestTime = referenceClock.referenceEpochTime
      val buffer = ntpPacketSerializer(requestPacket.copy(transmitEpochTimestamp = requestTime))
      ntpUdpSocketOperations.exchangePacketInPlace(
        buffer,
        address,
        NTP_PORT_NUMBER.toByte(),
      )
      val responseTime = referenceClock.referenceEpochTime - requestTime
      NtpExchangeResult(responseTime, ntpPacketDeserializer(buffer))
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
