package com.tidal.networktime.internal

import kotlinx.coroutines.withTimeout
import kotlin.time.Duration

internal class NTPExchangeCoordinator(
  private val referenceClock: KotlinXDateTimeSystemClock,
  private val ntpPacketSerializer: NTPPacketSerializer,
  private val ntpPacketDeserializer: NTPPacketDeserializer,
) {
  suspend operator fun invoke(
    address: String,
    connectTimeout: Duration,
    queryReadTimeout: Duration,
    ntpVersion: UByte,
  ): NTPExchangeResult? {
    val ntpUdpSocketOperations = NTPUDPSocketOperations()
    return try {
      withTimeout(connectTimeout) {
        ntpUdpSocketOperations.prepare(address, NTP_PORT_NUMBER)
      }
      val ntpPacket = NTPPacket(versionNumber = ntpVersion.toInt(), mode = NTP_MODE_CLIENT)
      val requestTime = referenceClock.referenceEpochTime
      ntpPacket.transmitEpochTimestamp = EpochTimestamp(requestTime).asNTPTimestamp
      val buffer = ntpPacketSerializer(ntpPacket)
      withTimeout(queryReadTimeout) {
        ntpUdpSocketOperations.exchange(buffer)
      }
      val returnTime = referenceClock.referenceEpochTime
      ntpPacketDeserializer(buffer)?.let { NTPExchangeResult(returnTime, it) }
    } catch (_: Throwable) {
      null
    } finally {
      ntpUdpSocketOperations.tearDown()
    }
  }

  companion object {
    private const val NTP_MODE_CLIENT = 3
    private const val NTP_PORT_NUMBER = 123
  }
}
