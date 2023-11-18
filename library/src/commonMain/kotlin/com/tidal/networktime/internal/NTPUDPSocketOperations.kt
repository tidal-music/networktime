package com.tidal.networktime.internal

internal expect class NTPUDPSocketOperations() {
  fun prepareSocket(timeoutMilliseconds: Long)

  fun exchangePacketInPlace(buffer: ByteArray, address: String, portNumber: Int)

  fun closeSocket()
}
