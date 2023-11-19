package com.tidal.networktime.internal

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class NTPUDPSocketOperations {
  actual fun prepareSocket(timeoutMilliseconds: Long) {}

  actual fun exchangePacketInPlace(buffer: ByteArray, address: String, portNumber: Int) {}

  actual fun closeSocket() {}
}
