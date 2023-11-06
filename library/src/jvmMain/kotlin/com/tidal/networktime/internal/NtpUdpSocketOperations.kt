package com.tidal.networktime.internal

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

internal actual class NtpUdpSocketOperations {
  private var datagramSocket: DatagramSocket? = null

  actual fun prepareSocket(timeoutMilliseconds: Long) {
    datagramSocket = DatagramSocket().apply { soTimeout = timeoutMilliseconds.toInt() }
  }

  actual fun exchangePacketInPlace(buffer: ByteArray, address: String, portNumber: Int) {
    val requestPacket =
      DatagramPacket(buffer, buffer.size, InetAddress.getByName(address), portNumber)
    datagramSocket!!.send(requestPacket)
    val responsePacket = DatagramPacket(buffer, buffer.size)
    datagramSocket!!.receive(responsePacket)
  }

  actual fun closeSocket() {
    datagramSocket?.close()
  }
}
