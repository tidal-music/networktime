package com.tidal.networktime

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Describes a host name that can resolve to any number of NTP unicast servers.
 *
 * @param hostName The host name.
 * @param queryConnectTimeout The per-server timeout for connecting to each of the servers resolved
 * from [hostName].
 * @param queryReadTimeout The per-server timeout for receiving responses from each of the servers
 * resolved from [hostName].
 * @param protocolFamilies Can be used for filtering addresses resolved from [hostName] based on
 * address family.
 * @param queriesPerResolvedAddress The amount of queries to perform to each resolved address. More
 * queries may or may not increase precision, but they will make synchronization take longer and
 * also cause more server load.
 * @param waitBetweenResolvedAddressQueries The amount of time to wait before consecutive requests
 * to the same resolved address.
 * @param ntpVersion The version number to write in packets.
 * @param maxRootDelay The maximum delay to accept a packet. Packets with a root delay higher than
 * this will be discarded.
 * @param maxRootDispersion The maximum root dispersion to accept a packet. Packets with a root
 * dispersion higher than this will be discarded.
 * @param dnsResolutionTimeout The timeout for DNS lookup for addresses from [hostName].
 */
class NTPServer(
  val hostName: String,
  val queryConnectTimeout: Duration = 5.seconds,
  val queryReadTimeout: Duration = 5.seconds,
  vararg val protocolFamilies: ProtocolFamily = arrayOf(ProtocolFamily.INET),
  val queriesPerResolvedAddress: Int = 3,
  val waitBetweenResolvedAddressQueries: Duration = 2.seconds,
  val ntpVersion: NTPVersion = NTPVersion.FOUR,
  val maxRootDelay: Duration = Duration.INFINITE,
  val maxRootDispersion: Duration = Duration.INFINITE,
  val dnsResolutionTimeout: Duration = 30.seconds,
)
