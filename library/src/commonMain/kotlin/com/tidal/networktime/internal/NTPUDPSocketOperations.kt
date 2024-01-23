package com.tidal.networktime.internal

import kotlin.time.Duration

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class NTPUDPSocketOperations() {
  suspend fun prepare(address: String, portNumber: Int, connectTimeout: Duration)

  suspend fun exchange(buffer: ByteArray, readTimeout: Duration)

  fun tearDown()
}
