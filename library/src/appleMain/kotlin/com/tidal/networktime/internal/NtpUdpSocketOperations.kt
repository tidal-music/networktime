package com.tidal.networktime.internal

internal actual class NtpUdpSocketOperations {
  actual fun prepareSocket(timeoutMilliseconds: Long) {}

  actual fun exchangePacketInPlace(buffer: ByteArray, address: String, portNumber: Int) {}

  actual fun closeSocket() {}
}
