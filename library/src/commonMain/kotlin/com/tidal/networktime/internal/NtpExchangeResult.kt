package com.tidal.networktime.internal

import kotlin.time.Duration

internal data class NtpExchangeResult(
  val timeMeasured: Duration,
  val ntpPacket: NtpPacket,
) {
  val roundTripDelay: Duration
    get() = timeMeasured - ntpPacket.run {
      originateEpochTimestamp -
        (
          transmitEpochTimestamp -
            receiveEpochTimestamp
          )
    }

  val clockOffset: Duration
    get() = ntpPacket.run {
      (
        receiveEpochTimestamp -
          originateEpochTimestamp +
          transmitEpochTimestamp -
          timeMeasured
        )
    } / 2
}
