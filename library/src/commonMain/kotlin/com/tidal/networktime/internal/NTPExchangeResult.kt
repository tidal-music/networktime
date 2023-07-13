@file:Suppress("DuplicatedCode") // We need the duplicated variable list for performance reasons

package com.tidal.networktime.internal

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

internal data class NTPExchangeResult(
  val returnTime: Duration,
  val ntpPacket: NTPPacket,
) {
  val roundTripDelay: Duration
    get() = ntpPacket.run {
      val originEpochMillis = originateEpochTimestamp.asEpochTimestamp.epochTime.inWholeMilliseconds
      val receiveNTPMillis = receiveEpochTimestamp.ntpTime.inWholeMilliseconds
      val receiveEpochMillis = receiveEpochTimestamp.asEpochTimestamp.epochTime.inWholeMilliseconds
      val transmitNTPMillis = transmitEpochTimestamp.ntpTime.inWholeMilliseconds
      val transmitEpochMillis = transmitEpochTimestamp.asEpochTimestamp
        .epochTime
        .inWholeMilliseconds
      val returnTimeMillis = returnTime.inWholeMilliseconds
      if (receiveNTPMillis == 0L || transmitNTPMillis == 0L) {
        return@run if (returnTimeMillis >= originEpochMillis) {
          (returnTimeMillis - originEpochMillis).milliseconds
        } else {
          Duration.INFINITE
        }
      }
      var delayMillis = returnTimeMillis - originEpochMillis
      val deltaMillis = transmitEpochMillis - receiveEpochMillis
      if (deltaMillis <= delayMillis) {
        delayMillis -= deltaMillis
      } else if (deltaMillis - delayMillis == 1L) {
        if (delayMillis != 0L) {
          delayMillis = 0
        }
      }
      delayMillis.milliseconds
    }

  val clockOffset: Duration
    get() = ntpPacket.run {
      val originNTPMillis = originateEpochTimestamp.ntpTime.inWholeMilliseconds
      val originEpochMillis = originateEpochTimestamp.asEpochTimestamp.epochTime.inWholeMilliseconds
      val receiveNTPMillis = receiveEpochTimestamp.ntpTime.inWholeMilliseconds
      val receiveEpochMillis = receiveEpochTimestamp.asEpochTimestamp.epochTime.inWholeMilliseconds
      val transmitNTPMillis = transmitEpochTimestamp.ntpTime.inWholeMilliseconds
      val transmitEpochMillis = transmitEpochTimestamp.asEpochTimestamp
        .epochTime
        .inWholeMilliseconds
      val returnTimeMillis = returnTime.inWholeMilliseconds
      if (originNTPMillis == 0L) {
        if (transmitNTPMillis != 0L) {
          return@run (transmitEpochMillis - returnTimeMillis).milliseconds
        }
        return@run Duration.INFINITE
      }
      if (receiveNTPMillis == 0L || transmitNTPMillis == 0L) {
        if (receiveNTPMillis != 0L) {
          return@run (receiveEpochMillis - originEpochMillis).milliseconds
        }
        if (transmitNTPMillis != 0L) {
          return@run (transmitEpochMillis - returnTimeMillis).milliseconds
        }
        return@run Duration.INFINITE
      }
      ((receiveEpochMillis - originEpochMillis + transmitEpochMillis - returnTimeMillis) / 2)
        .milliseconds
    }
}
