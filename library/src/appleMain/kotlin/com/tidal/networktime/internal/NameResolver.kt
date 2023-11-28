package com.tidal.networktime.internal

import kotlinx.cinterop.BooleanVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.alloc
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
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
import platform.posix.AF_INET
import platform.posix.AF_INET6
import platform.posix.sockaddr
import kotlin.time.Duration

@OptIn(ExperimentalForeignApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class NameResolver {
  private var cfHost: CFHostRef? = null
  private var hostReference: CFTypeRef? = null
  private var userDataRef: StableRef<UserData>? = null

  actual suspend operator fun invoke(
    name: String,
    timeout: Duration,
    includeIPv4: Boolean,
    includeIPv6: Boolean,
  ): Iterable<String> {
    var ret: Iterable<String>? = emptySet()
    try {
      ret = withTimeoutOrNull(timeout) { invokeInternal(name, includeIPv4, includeIPv6) }
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
    name: String,
    includeIPv4: Boolean,
    includeIPv6: Boolean,
  ): Iterable<String> {
    val callback: CFHostClientCallBack = staticCFunction { host, _, _, info ->
      val addresses = memScoped {
        val hasBeenResolved = alloc<BooleanVar>().apply {
          value = false
        }
        CFHostGetAddressing(host!!, hasBeenResolved.ptr).takeIf { hasBeenResolved.value }
          ?: return@staticCFunction
      }
      val userData = info!!.asStableRef<UserData>().get()
      val addressCount = CFArrayGetCount(addresses)
      userData.resolvedAddresses = (0 until addressCount).mapNotNull {
        CFArrayGetValueAtIndex(addresses, it)
          ?.reinterpret<sockaddr>()
          ?.pointed
          ?.takeIf {
            when (it.sa_family.toInt()) {
              AF_INET -> userData.includeIPv4
              AF_INET6 -> userData.includeIPv6
              else -> false
            }
          }
      }.map {
        it.sa_data.toKString()
      }
    }
    hostReference = CFBridgingRetain(name as NSString)
    val cfHost = CFHostCreateWithName(kCFAllocatorDefault, hostReference!! as CFStringRef)
    userDataRef = StableRef.create(UserData(includeIPv4, includeIPv6))
    memScoped {
      val clientContext = alloc<CFHostClientContext>().apply {
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

  private class UserData(
    val includeIPv4: Boolean,
    val includeIPv6: Boolean,
  ) {
    var resolvedAddresses: Iterable<String> = emptySet()
  }
}
