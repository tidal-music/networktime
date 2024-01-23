package com.tidal.networktime.internal

import kotlinx.coroutines.withTimeout
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import kotlin.time.Duration
import kotlin.time.DurationUnit

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "BlockingMethodInNonBlockingContext")
internal actual class NTPUDPSocketOperations {
  private var datagramSocket: DatagramSocket? = null

  actual suspend fun prepare(address: String, portNumber: Int, connectTimeout: Duration) {
    datagramSocket = DatagramSocket()
    withTimeout(connectTimeout) {
      datagramSocket!!.connect(InetAddress.getByName(address), portNumber)
    }
  }

  actual suspend fun exchange(buffer: ByteArray, readTimeout: Duration) {
    val exchangePacket = DatagramPacket(buffer, buffer.size)
    datagramSocket!!.send(exchangePacket)
    datagramSocket!!.soTimeout = readTimeout.toInt(DurationUnit.MILLISECONDS)
    datagramSocket!!.receive(exchangePacket)
  }

  actual fun tearDown() {
    datagramSocket?.close()
  }
}
