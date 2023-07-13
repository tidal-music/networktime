package com.tidal.networktime.singletons.internal

import com.tidal.networktime.NTPServer
import com.tidal.networktime.SNTPClient
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized

internal object SingletonSNTPClientMap : SynchronizedObject() {
  private val delegate = mutableMapOf<String, SNTPClient>()

  fun getOrAdd(sntpClient: SNTPClient) = synchronized(this) {
    val key = sntpClient.asKey
    val retrievedValue = delegate[key]
    if (retrievedValue != null) {
      return@synchronized retrievedValue
    }
    delegate[key] = sntpClient
    return sntpClient
  }

  private val SNTPClient.asKey: String
    get() = run {
      StringBuilder().apply {
        appendLine(ntpServers.map { it.asKeyFragment }.sorted())
        appendLine(synchronizationInterval)
        appendLine(backupFilePath)
      }.toString()
    }

  private val NTPServer.asKeyFragment: String
    get() = run {
      StringBuilder().apply {
        appendLine(hostName)
        appendLine(queryConnectTimeout)
        appendLine(queryReadTimeout)
        appendLine(protocolFamilies.map { it.name }.sorted())
        appendLine(queriesPerResolvedAddress)
        appendLine(waitBetweenResolvedAddressQueries)
        appendLine(ntpVersion)
        appendLine(maxRootDelay)
        appendLine(maxRootDispersion)
        appendLine(dnsResolutionTimeout)
      }.toString()
    }
}
