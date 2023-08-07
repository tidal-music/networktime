package com.tidal.networktime

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Describes a host name that can resolve to any number of NTP unicast servers.
 *
 * @param hostName The host name.
 * @param responseTimeout The timeout for receiving responses from servers resolved from [hostName].
 * @param dnsResolutionStrategy Can be used for filtering resolved address on [hostName] based on
 * IP version.
 * @param queriesPerResolvedAddress The amount of queries to perform to each resolved address. More
 * queries may or may not increase precision, but they will make synchronization take longer and
 * also cause more server load.
 * @param waitBetweenResolvedAddressQueries The amount of time to wait before consecutive requests
 * to the same resolved address.
 * @param pinnedPortNumber The port number to send packets on. If null, a random choice of port
 * be made for every packet, as recommended by RFC 9109.
 * @param ntpVersion The version number to write in packets.
 */
class NTPServer(
  val hostName: String,
  val responseTimeout: Duration = 5.seconds,
  val dnsResolutionStrategy: DNSResolutionStrategy = DNSResolutionStrategy.ALL,
  val queriesPerResolvedAddress: Short = 3,
  val waitBetweenResolvedAddressQueries: Duration = 2.seconds,
  val pinnedPortNumber: Int? = null,
  val ntpVersion: NTPVersion = NTPVersion.FOUR,
) {

  enum class DNSResolutionStrategy {
    IP_V4,
    IP_V6,
    ALL,
  }

  enum class NTPVersion(val descriptor: Short) {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
  }
}
