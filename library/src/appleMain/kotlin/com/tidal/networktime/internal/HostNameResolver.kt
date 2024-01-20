package com.tidal.networktime.internal

import com.tidal.networktime.ProtocolFamily
import kotlinx.cinterop.BooleanVar
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readValue
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import kotlinx.cinterop.value
import kotlinx.coroutines.withTimeoutOrNull
import platform.CFNetwork.CFHostCancelInfoResolution
import platform.CFNetwork.CFHostCreateWithName
import platform.CFNetwork.CFHostGetAddressing
import platform.CFNetwork.CFHostRef
import platform.CFNetwork.CFHostStartInfoResolution
import platform.CFNetwork.kCFHostAddresses
import platform.CoreFoundation.CFArrayGetCount
import platform.CoreFoundation.CFArrayGetValueAtIndex
import platform.CoreFoundation.CFDataGetBytePtr
import platform.CoreFoundation.CFDataRef
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.CFTypeRef
import platform.CoreFoundation.kCFAllocatorDefault
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSString
import platform.darwin.inet_ntop
import platform.posix.AF_INET
import platform.posix.AF_INET6
import platform.posix.INET6_ADDRSTRLEN
import platform.posix.INET_ADDRSTRLEN
import platform.posix.sockaddr
import platform.posix.sockaddr_in
import platform.posix.sockaddr_in6
import kotlin.time.Duration

@OptIn(ExperimentalForeignApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class HostNameResolver {
  private var cfHost: CFHostRef? = null
  private var hostReference: CFTypeRef? = null

  actual suspend operator fun invoke(
    hostName: String,
    timeout: Duration,
    includeINET: Boolean,
    includeINET6: Boolean,
  ): Iterable<Pair<String, ProtocolFamily>> {
    var ret: Iterable<Pair<String, ProtocolFamily>>? = emptySet()
    try {
      ret = withTimeoutOrNull(timeout) { invokeInternal(hostName, includeINET, includeINET6) }
    } finally {
      cfHost
        ?.takeIf { ret == null }
        ?.let {
          CFHostCancelInfoResolution(it, kCFHostAddresses)
        }
      clear()
    }
    return ret ?: emptySet()
  }

  private fun invokeInternal(
    hostName: String,
    includeINET: Boolean,
    includeINET6: Boolean,
  ): Iterable<Pair<String, ProtocolFamily>> {
    hostReference = CFBridgingRetain(hostName as NSString)
    cfHost = CFHostCreateWithName(kCFAllocatorDefault, hostReference as CFStringRef)
    CFHostStartInfoResolution(cfHost, kCFHostAddresses, null)
    return memScoped {
      val hasResolved = alloc<BooleanVar> {
        value = false
      }
      val addresses = CFHostGetAddressing(cfHost, hasResolved.ptr)
      addresses.takeIf { hasResolved.value }
      addresses ?: return emptySet()
      val count = CFArrayGetCount(addresses)
      val ret = mutableSetOf<Pair<String, ProtocolFamily>>()
      (0 until count).forEach {
        val socketAddressData = CFArrayGetValueAtIndex(addresses, it) as CFDataRef
        val sockAddr = CFDataGetBytePtr(socketAddressData)!!.reinterpret<sockaddr>().pointed
        val addrPrettyToProtocolFamily = when (sockAddr.sa_family.toInt()) {
          AF_INET -> {
            if (includeINET) {
              val buffer = allocArray<ByteVar>(INET_ADDRSTRLEN)
              inet_ntop(
                AF_INET,
                sockAddr.reinterpret<sockaddr_in>().sin_addr.readValue(),
                buffer,
                INET_ADDRSTRLEN.toUInt(),
              )
              buffer.toKString() to ProtocolFamily.INET
            } else {
              null
            }
          }

          AF_INET6 -> {
            if (includeINET6) {
              val buffer = allocArray<ByteVar>(INET6_ADDRSTRLEN)
              inet_ntop(
                AF_INET6,
                sockAddr.reinterpret<sockaddr_in6>().sin6_addr.readValue(),
                buffer,
                INET6_ADDRSTRLEN.toUInt(),
              )
              buffer.toKString() to ProtocolFamily.INET6
            } else {
              null
            }
          }

          else -> {
            null
          }
        }
        if (addrPrettyToProtocolFamily != null) {
          ret.add(addrPrettyToProtocolFamily)
        }
      }
      ret
    }
  }

  private fun clear() {
    cfHost = null
    hostReference?.let { CFRelease(it) }
    hostReference = null
  }
}
