package com.tidal.networktime.internal

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

internal actual class NtpUdpSocketOperations {
  private lateinit var datagramSocket: DatagramSocket

  actual fun prepareSocket(timeoutMilliseconds: Long) {
    datagramSocket = DatagramSocket().apply { soTimeout = timeoutMilliseconds.toInt() }
  }

  actual fun exchangePacketInPlace(buffer: ByteArray, address: String, portNumber: UInt) {
    val requestPacket =
      DatagramPacket(buffer, buffer.size, InetAddress.getByName(address), portNumber.toInt())
    datagramSocket.send(requestPacket)
    val responsePacket = DatagramPacket(buffer, buffer.size)
    datagramSocket.receive(responsePacket)
  }

  actual fun closeSocket() = datagramSocket.close()
}