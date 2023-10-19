package com.tidal.networktime.internal

import com.tidal.networktime.ReadableClock
import kotlin.random.Random
import kotlin.random.nextUInt
import kotlin.time.Duration

internal class NtpExchanger(
  private val referenceClock: ReadableClock,
  private val ntpPacketSerializer: NtpPacketSerializer,
  private val ntpPacketDeserializer: NtpPacketDeserializer,
  private val random: Random,
) {
  operator fun invoke(
    address: String,
    queryTimeout: Duration,
    portNumber: UInt?,
    ntpVersion: UInt,
  ): NtpExchangeResult? {
    val ntpUdpSocketOperations = NtpUdpSocketOperations()
    val requestPacket = NtpPacket(
      versionNumber = ntpVersion.toByte(),
      mode = NTP_MODE_CLIENT.toByte(),
    )
    return try {
      ntpUdpSocketOperations.prepareSocket(queryTimeout.inWholeMilliseconds)
      val requestTime = referenceClock.epochTime
      val buffer = ntpPacketSerializer(requestPacket.copy(originateEpochTimestamp = requestTime))
      ntpUdpSocketOperations.exchangePacketInPlace(
        buffer,
        address,
        portNumber ?: random.nextUInt(),
      )
      val responseTime = referenceClock.epochTime - requestTime
      NtpExchangeResult(responseTime, ntpPacketDeserializer(buffer))
    } catch (_: Throwable) {
      null
    } finally {
      ntpUdpSocketOperations.closeSocket()
    }
  }

  companion object {
    private const val NTP_MODE_CLIENT = 3
  }
}
