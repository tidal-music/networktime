package com.tidal.networktime.internal

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class NTPUDPSocketOperations() {
  suspend fun prepare(address: String, portNumber: Int)

  suspend fun exchange(buffer: ByteArray)

  fun tearDown()
}
