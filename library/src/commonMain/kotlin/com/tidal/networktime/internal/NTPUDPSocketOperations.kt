package com.tidal.networktime.internal

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class NTPUDPSocketOperations() {
  fun prepareSocket(timeoutMilliseconds: Long)

  fun exchangePacketInPlace(buffer: ByteArray, address: String, portNumber: Int)

  fun closeSocket()
}
