package com.tidal.networktime.internal

import com.tidal.networktime.ProtocolFamily
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.alloc
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.refTo
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong
import kotlinx.cinterop.value
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import platform.CoreFoundation.CFDataCreate
import platform.CoreFoundation.CFDataGetBytes
import platform.CoreFoundation.CFDataGetLength
import platform.CoreFoundation.CFDataRefVar
import platform.CoreFoundation.CFRangeMake
import platform.CoreFoundation.CFSocketCallBack
import platform.CoreFoundation.CFSocketConnectToAddress
import platform.CoreFoundation.CFSocketContext
import platform.CoreFoundation.CFSocketCreate
import platform.CoreFoundation.CFSocketInvalidate
import platform.CoreFoundation.CFSocketRef
import platform.CoreFoundation.CFSocketSendData
import platform.CoreFoundation.kCFAllocatorDefault
import platform.CoreFoundation.kCFSocketDataCallBack
import platform.darwin.inet_aton
import platform.darwin.inet_pton
import platform.posix.AF_INET
import platform.posix.AF_INET6
import platform.posix.IPPROTO_UDP
import platform.posix.PF_INET
import platform.posix.PF_INET6
import platform.posix.SIGPIPE
import platform.posix.SIG_IGN
import platform.posix.SOCK_DGRAM
import platform.posix.signal
import platform.posix.sockaddr_in
import platform.posix.sockaddr_in6
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@OptIn(ExperimentalForeignApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class NTPUDPSocketOperations {
  private var cfSocket: CFSocketRef? = null
  private var userDataRef: StableRef<UserData>? = null

  actual suspend fun prepareSocket(
    address: String,
    protocolFamily: ProtocolFamily,
    portNumber: Int,
    connectTimeout: Duration,
  ) {
    userDataRef = StableRef.create(UserData())
    val callback: CFSocketCallBack = staticCFunction { _, callbackType, _, data, info ->
      val userData = info!!.asStableRef<UserData>().get()
      if (callbackType != kCFSocketDataCallBack) {
        return@staticCFunction
      }
      val reinterpretedData = data!!.reinterpret<CFDataRefVar>().pointed.value
      val length = CFDataGetLength(reinterpretedData)
      val range = CFRangeMake(0, length)
      val bridgeBuffer = UByteArray(length.toInt())
      CFDataGetBytes(reinterpretedData, range, bridgeBuffer.refTo(0))
      userData.apply {
        buffer = bridgeBuffer.toByteArray()
        exchangeCompleted = true
      }
    }
    signal(SIGPIPE, SIG_IGN)
    cfSocket = memScoped {
      val socketContext = alloc<CFSocketContext> {
        version = 0
        info = userDataRef!!.asCPointer()
        retain = null
        release = null
        copyDescription = null
      }
      val socket = CFSocketCreate(
        kCFAllocatorDefault,
        when (protocolFamily) {
          ProtocolFamily.INET -> PF_INET
          ProtocolFamily.INET6 -> PF_INET6
        },
        SOCK_DGRAM,
        IPPROTO_UDP,
        kCFSocketDataCallBack,
        callback,
        socketContext.ptr,
      )
      val addrCFDataRef = when (protocolFamily) {
        ProtocolFamily.INET -> alloc<sockaddr_in> {
          sin_family = AF_INET.toUByte()
          sin_port = portNumber.toUShort()
          inet_aton(address, sin_addr.ptr)
        }.run {
          CFDataCreate(kCFAllocatorDefault, ptr.toLong().toCPointer(), sizeOf<sockaddr_in>())
        }

        ProtocolFamily.INET6 -> {
          alloc<sockaddr_in6> {
            sin6_family = AF_INET6.toUByte()
            sin6_port = portNumber.toUShort()
            inet_pton(AF_INET6, address, sin6_addr.ptr)
          }.run {
            CFDataCreate(kCFAllocatorDefault, ptr.toLong().toCPointer(), sizeOf<sockaddr_in6>())
          }
        }
      }
      CFSocketConnectToAddress(
        socket,
        addrCFDataRef,
        connectTimeout.toDouble(DurationUnit.MILLISECONDS),
      )
      socket
    }
  }

  actual suspend fun exchangeInPlace(buffer: ByteArray, readTimeout: Duration) {
    val bufferCFDataRef = CFDataCreate(
      kCFAllocatorDefault,
      buffer.asUByteArray().refTo(0),
      buffer.size.toLong(),
    )
    CFSocketSendData(
      cfSocket,
      null,
      bufferCFDataRef,
      Duration.INFINITE.toDouble(DurationUnit.MILLISECONDS),
    )
    withTimeout(readTimeout) {
      while (!userDataRef!!.get().exchangeCompleted) {
        delay(1.seconds)
      }
    }
  }

  actual fun closeSocket() {
    CFSocketInvalidate(cfSocket)
    cfSocket = null
    userDataRef?.dispose()
    userDataRef = null
  }

  private class UserData {
    var exchangeCompleted = false
    var buffer: ByteArray = byteArrayOf()
  }
}
