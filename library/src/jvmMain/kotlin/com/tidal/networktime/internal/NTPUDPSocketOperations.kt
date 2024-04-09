package com.tidal.networktime.internal

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "BlockingMethodInNonBlockingContext")
internal actual class NTPUDPSocketOperations {
  private var datagramSocket: DatagramSocket? = null

  actual suspend fun prepare(address: String, portNumber: Int) {
    datagramSocket = DatagramSocket()
    datagramSocket!!.connect(InetAddress.getByName(address), portNumber)
  }

  actual suspend fun exchange(buffer: ByteArray) {
    val exchangePacket = DatagramPacket(buffer, buffer.size)
    datagramSocket!!.send(exchangePacket)
    datagramSocket!!.receive(exchangePacket)
  }

  actual fun tearDown() {
    datagramSocket?.close()
  }
}
