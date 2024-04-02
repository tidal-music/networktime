package com.tidal.networktime.internal

import com.tidal.networktime.internal.network_framework_workaround.nw_connection_send_default_context
import com.tidal.networktime.internal.network_framework_workaround.nw_parameters_create_secure_udp_disable_protocol
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.pin
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withTimeout
import platform.Network.nw_connection_create
import platform.Network.nw_connection_force_cancel
import platform.Network.nw_connection_receive
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
import platform.darwin.dispatch_data_apply
import platform.darwin.dispatch_data_create
import platform.darwin.dispatch_data_t
import platform.darwin.dispatch_get_current_queue
import platform.posix.memcpy
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.time.Duration

@OptIn(ExperimentalForeignApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class NTPUDPSocketOperations {
  private var connection: nw_connection_t = null

  actual suspend fun prepare(address: String, portNumber: Int, connectTimeout: Duration) {
    println("BEFORE CREATESECUREUDP")
    val parameters = nw_parameters_create_secure_udp_disable_protocol()
    println("BEFORE CREATEHOST")
    println("Endpoint is $address/$portNumber")
    val endpoint = nw_endpoint_create_host(address, portNumber.toString())
    println("BEFORE CONNCREATE")
    connection = nw_connection_create(endpoint, parameters)
    println("BEFORE SETQUEUE")
    nw_connection_set_queue(connection, dispatch_get_current_queue())
    val connectionStateDeferred = CompletableDeferred<nw_connection_state_t>()
    println("BEFORE SETHANDLER")
    nw_connection_set_state_changed_handler(connection) { state: nw_connection_state_t, _ ->
      println("State is $state")
      when (state) {
        nw_connection_state_ready, nw_connection_state_failed, nw_connection_state_cancelled ->
          connectionStateDeferred.complete(state)
      }
    }
    println("Print test")
    println("BEFORE START")
    nw_connection_start(connection)
    println("BEFORE WITHTIMEOUT")
    withTimeout(connectTimeout) {
      assertEquals(nw_connection_state_ready, connectionStateDeferred.await())
    }
    println("AFTER WITHTIMEOUT")
  }

  actual suspend fun exchange(buffer: ByteArray, readTimeout: Duration) {
    println("EXCHANGE")
    val data = buffer.pin().run {
      dispatch_data_create(
        addressOf(0),
        buffer.size.convert(),
        dispatch_get_current_queue(),
        ({ unpin() }),
      )
    }
    println("BEFORE SEND")
    nw_connection_send_default_context(
      connection,
      data,
      true,
    ) {
      println("SEND CB, ERROR IS $it")
      assertNull(it)
    }
    val connectionReceptionDeferred = CompletableDeferred<dispatch_data_t>()
    println("BEFORE RECEIVE")
    nw_connection_receive(
      connection,
      1.convert(),
      buffer.size.convert(),
    ) { content: dispatch_data_t, _, _, error: nw_error_t ->
      println("RECEIVE CB, ERROR IS $error")
      assertNull(error)
      connectionReceptionDeferred.complete(content)
    }
    println("BEFORE RECEIVE TIMEOUT")
    val receivedData = withTimeout(readTimeout) {
      connectionReceptionDeferred.await()
    }
    println("BEFORE USEPINNED FOR RECEIVED")
    buffer.usePinned {
      dispatch_data_apply(receivedData) { _, offset, src, size ->
        memcpy(it.addressOf(offset.toInt()), src, size)
        true
      }
    }
  }

  actual fun tearDown() {
    println("TEARDOWN")
    nw_connection_force_cancel(connection)
    connection = null
  }
}
