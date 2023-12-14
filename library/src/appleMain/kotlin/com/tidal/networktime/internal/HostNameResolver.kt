package com.tidal.networktime.internal

import com.tidal.networktime.ProtocolFamily
import kotlinx.cinterop.BooleanVar
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readValue
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import kotlinx.cinterop.value
import kotlinx.coroutines.withTimeoutOrNull
import platform.CFNetwork.CFHostCancelInfoResolution
import platform.CFNetwork.CFHostClientCallBack
import platform.CFNetwork.CFHostClientContext
import platform.CFNetwork.CFHostCreateWithName
import platform.CFNetwork.CFHostGetAddressing
import platform.CFNetwork.CFHostRef
import platform.CFNetwork.CFHostSetClient
import platform.CFNetwork.CFHostStartInfoResolution
import platform.CFNetwork.kCFHostAddresses
import platform.CoreFoundation.CFArrayGetCount
import platform.CoreFoundation.CFArrayGetValueAtIndex
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.CFTypeRef
import platform.CoreFoundation.kCFAllocatorDefault
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSString
import platform.darwin.inet_ntoa
import platform.darwin.inet_ntop
import platform.posix.AF_INET
import platform.posix.AF_INET6
import platform.posix.sockaddr
import platform.posix.sockaddr_in
import platform.posix.sockaddr_in6
import kotlin.time.Duration

@OptIn(ExperimentalForeignApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class HostNameResolver {
  private var cfHost: CFHostRef? = null
  private var hostReference: CFTypeRef? = null
  private var userDataRef: StableRef<UserData>? = null

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
          CFHostSetClient(it, null, null)
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
    val callback: CFHostClientCallBack = staticCFunction { host, _, _, info ->
      val addresses = memScoped {
        val hasBeenResolved = alloc<BooleanVar> {
          value = false
        }
        CFHostGetAddressing(host!!, hasBeenResolved.ptr).takeIf { hasBeenResolved.value }
          ?: return@staticCFunction
      }
      val userData = info!!.asStableRef<UserData>().get()
      val addressCount = CFArrayGetCount(addresses)
      userData.resolvedAddresses = (0 until addressCount).mapNotNull {
        val it = CFArrayGetValueAtIndex(addresses, it)
          ?.reinterpret<sockaddr>()
          ?.pointed
          ?.takeIf {
            when (it.sa_family.toInt()) {
              AF_INET -> userData.includeINET
              AF_INET6 -> userData.includeINET6
              else -> false
            }
          }?.run {
            when (sa_family.toInt()) {
              AF_INET -> reinterpret<sockaddr_in>()
              AF_INET6 -> reinterpret<sockaddr_in6>()
              else -> false
            }
          } ?: return@mapNotNull null
        return@mapNotNull when (it) {
          is sockaddr_in -> inet_ntoa(it.sin_addr.readValue())!!.toKString() to ProtocolFamily.INET

          is sockaddr_in6 -> {
            val addressString: String = memScoped {
              val buffer = allocArray<ByteVar>(it.sin6_len.toInt())
              inet_ntop(AF_INET6, it.sin6_addr.readValue(), buffer, it.sin6_len.toUInt())
              buffer.toKString()
            }
            addressString to ProtocolFamily.INET6
          }

          else -> null
        }
      }
    }
    hostReference = CFBridgingRetain(hostName as NSString)
    cfHost = CFHostCreateWithName(kCFAllocatorDefault, hostReference!! as CFStringRef)
    userDataRef = StableRef.create(UserData(includeINET, includeINET6))
    memScoped {
      val clientContext = alloc<CFHostClientContext> {
        version = 0
        info = userDataRef!!.asCPointer()
        retain = null
        release = null
        copyDescription = null
      }
      CFHostSetClient(cfHost, callback, clientContext.ptr)
      CFHostStartInfoResolution(cfHost, kCFHostAddresses, null)
    }
    return userDataRef?.get()?.resolvedAddresses ?: emptySet()
  }

  private fun clear() {
    cfHost = null
    hostReference?.let { CFBridgingRelease(it) }
    hostReference = null
    userDataRef?.dispose()
    userDataRef = null
  }

  private class UserData(val includeINET: Boolean, val includeINET6: Boolean) {
    var resolvedAddresses: Iterable<Pair<String, ProtocolFamily>> = emptySet()
  }
}
