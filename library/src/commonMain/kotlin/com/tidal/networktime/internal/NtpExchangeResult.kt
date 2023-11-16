@file:Suppress("DuplicatedCode") // We need the duplicated variable list for performance reasons

package com.tidal.networktime.internal

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

internal data class NtpExchangeResult(
  val returnTime: Duration,
  val ntpPacket: NtpPacket,
) {
  val roundTripDelay: Duration
    get() = ntpPacket.run {
      val originEpochMillis = originateEpochTimestamp.asEpochTimestamp.epochTime.inWholeMilliseconds
      val receiveNtpMillis = receiveEpochTimestamp.ntpTime.inWholeMilliseconds
      val receiveEpochMillis = receiveEpochTimestamp.asEpochTimestamp.epochTime.inWholeMilliseconds
      val transmitNtpMillis = transmitEpochTimestamp.ntpTime.inWholeMilliseconds
      val transmitEpochMillis = transmitEpochTimestamp.asEpochTimestamp
        .epochTime
        .inWholeMilliseconds
      val returnTimeMillis = returnTime.inWholeMilliseconds
      if (receiveNtpMillis == 0L || transmitNtpMillis == 0L) {
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
      val originNtpMillis = originateEpochTimestamp.ntpTime.inWholeMilliseconds
      val originEpochMillis = originateEpochTimestamp.asEpochTimestamp.epochTime.inWholeMilliseconds
      val receiveNtpMillis = receiveEpochTimestamp.ntpTime.inWholeMilliseconds
      val receiveEpochMillis = receiveEpochTimestamp.asEpochTimestamp.epochTime.inWholeMilliseconds
      val transmitNtpMillis = transmitEpochTimestamp.ntpTime.inWholeMilliseconds
      val transmitEpochMillis = transmitEpochTimestamp.asEpochTimestamp
        .epochTime
        .inWholeMilliseconds
      val returnTimeMillis = returnTime.inWholeMilliseconds
      if (originNtpMillis == 0L) {
        if (transmitNtpMillis != 0L) {
          return@run (transmitEpochMillis - returnTimeMillis).milliseconds
        }
        return@run Duration.INFINITE
      }
      if (receiveNtpMillis == 0L || transmitNtpMillis == 0L) {
        if (receiveNtpMillis != 0L) {
          return@run (receiveEpochMillis - originEpochMillis).milliseconds
        }
        if (transmitNtpMillis != 0L) {
          return@run (transmitEpochMillis - returnTimeMillis).milliseconds
        }
        return@run Duration.INFINITE
      }
      ((receiveEpochMillis - originEpochMillis + transmitEpochMillis - returnTimeMillis) / 2)
        .milliseconds
    }
}
