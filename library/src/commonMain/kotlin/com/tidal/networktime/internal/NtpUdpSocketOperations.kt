package com.tidal.networktime.internal

internal expect class NtpUdpSocketOperations() {
  fun prepareSocket(timeoutMilliseconds: Long)

  fun exchangePacketInPlace(buffer: ByteArray, address: String, portNumber: Int)

  fun closeSocket()
}
