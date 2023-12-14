package com.tidal.networktime.internal

import com.tidal.networktime.ProtocolFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import kotlin.time.Duration
import kotlin.time.DurationUnit

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class NTPUDPSocketOperations {
  private lateinit var datagramSocket: DatagramSocket

  actual suspend fun prepareSocket(
    address: String,
    protocolFamily: ProtocolFamily,
    portNumber: Int,
    connectTimeout: Duration,
  ) = withTimeout(connectTimeout) {
    datagramSocket = DatagramSocket()
    datagramSocket.connect(InetAddress.getByName(address), portNumber)
  }

  actual suspend fun exchangeInPlace(buffer: ByteArray, readTimeout: Duration) {
    val exchangePacket = DatagramPacket(buffer, buffer.size)
    withContext(Dispatchers.IO) {
      datagramSocket.send(exchangePacket)
      datagramSocket.soTimeout = readTimeout.toInt(DurationUnit.MILLISECONDS)
      datagramSocket.receive(exchangePacket)
    }
  }

  actual fun closeSocket() {
    datagramSocket.close()
  }
}
