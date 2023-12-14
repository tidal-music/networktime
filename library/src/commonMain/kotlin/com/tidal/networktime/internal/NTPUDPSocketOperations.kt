package com.tidal.networktime.internal

import com.tidal.networktime.ProtocolFamily
import kotlin.time.Duration

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class NTPUDPSocketOperations() {
  suspend fun prepareSocket(
    address: String,
    protocolFamily: ProtocolFamily,
    portNumber: Int,
    connectTimeout: Duration,
  )

  suspend fun exchangeInPlace(buffer: ByteArray, readTimeout: Duration)

  fun closeSocket()
}
