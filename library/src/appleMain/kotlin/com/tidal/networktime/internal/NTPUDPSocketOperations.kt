package com.tidal.networktime.internal

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.refTo
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withTimeout
import platform.Network.NW_CONNECTION_FINAL_MESSAGE_CONTEXT
import platform.Network.NW_PARAMETERS_DEFAULT_CONFIGURATION
import platform.Network.NW_PARAMETERS_DISABLE_PROTOCOL
import platform.Network.nw_connection_create
import platform.Network.nw_connection_force_cancel
import platform.Network.nw_connection_receive
import platform.Network.nw_connection_send
import platform.Network.nw_connection_set_queue
import platform.Network.nw_connection_set_state_changed_handler
import platform.Network.nw_connection_start
import platform.Network.nw_connection_state_cancelled
import platform.Network.nw_connection_state_failed
import platform.Network.nw_connection_state_ready
import platform.Network.nw_connection_state_t
import platform.Network.nw_connection_t
import platform.Network.nw_endpoint_create_host
import platform.Network.nw_error_t
import platform.Network.nw_parameters_create_secure_udp
import platform.darwin._dispatch_data_destructor_free
import platform.darwin.dispatch_data_apply
import platform.darwin.dispatch_data_create
import platform.darwin.dispatch_data_t
import platform.darwin.dispatch_get_current_queue
import platform.posix.memcpy
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.time.Duration

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class NTPUDPSocketOperations {
  private var connection: nw_connection_t = null

  actual suspend fun prepare(address: String, portNumber: Int, connectTimeout: Duration) {
    val parameters = nw_parameters_create_secure_udp(
      NW_PARAMETERS_DISABLE_PROTOCOL,
      NW_PARAMETERS_DEFAULT_CONFIGURATION,
    )
    val endpoint = nw_endpoint_create_host(address, portNumber.toString())
    connection = nw_connection_create(endpoint, parameters)
    nw_connection_set_queue(connection, dispatch_get_current_queue())
    val connectionStateDeferred = CompletableDeferred<nw_connection_state_t>()
    nw_connection_set_state_changed_handler(connection) { state: nw_connection_state_t, _ ->
      when (state) {
        nw_connection_state_ready, nw_connection_state_failed, nw_connection_state_cancelled ->
          connectionStateDeferred.complete(state)
      }
    }
    nw_connection_start(connection)
    withTimeout(connectTimeout) {
      assertEquals(nw_connection_state_ready, connectionStateDeferred.await())
    }
  }

  @OptIn(ExperimentalForeignApi::class)
  actual suspend fun exchange(buffer: ByteArray, readTimeout: Duration) {
    val toSendData = memScoped {
      val cArray = allocArray<ByteVar>(buffer.size)
      buffer.forEachIndexed { i, it ->
        cArray[i] = it
      }
      cArray
    }
    nw_connection_send(
      connection,
      dispatch_data_create(toSendData, buffer.size.convert(), null, _dispatch_data_destructor_free),
      NW_CONNECTION_FINAL_MESSAGE_CONTEXT,
      true,
    ) {
      assertNull(it)
    }
    val connectionReceptionDeferred = CompletableDeferred<dispatch_data_t>()
    nw_connection_receive(
      connection,
      1.convert(),
      buffer.size.convert(),
    ) { content: dispatch_data_t, _, _, error: nw_error_t ->
      assertNull(error)
      connectionReceptionDeferred.complete(content)
    }
    val receivedData = withTimeout(readTimeout) {
      connectionReceptionDeferred.await()
    }
    dispatch_data_apply(receivedData) { _, _, regionPointer, _ ->
      memcpy(buffer.refTo(0), regionPointer!!.reinterpret<ByteVar>(), buffer.size.convert())
      false
    }
  }

  actual fun tearDown() {
    nw_connection_force_cancel(connection)
    connection = null
  }
}
