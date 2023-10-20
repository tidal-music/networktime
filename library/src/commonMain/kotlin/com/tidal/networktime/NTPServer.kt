package com.tidal.networktime

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Describes a host name that can resolve to any number of NTP unicast servers.
 *
 * @param name The host name.
 * @param lookupTimeout The timeout for DNS lookup over HTTPs.
 * @param queryTimeout The timeout for receiving responses from servers resolved from [name].
 * @param dnsLookupStrategy Can be used for filtering resolved address on [name] based on
 * IP version.
 * @param queriesPerResolvedAddress The amount of queries to perform to each resolved address. More
 * queries may or may not increase precision, but they will make synchronization take longer and
 * also cause more server load.
 * @param waitBetweenResolvedAddressQueries The amount of time to wait before consecutive requests
 * to the same resolved address.
 * @param ntpVersion The version number to write in packets.
 */
class NTPServer(
  val name: String,
  val lookupTimeout: Duration = 3.seconds,
  val queryTimeout: Duration = 5.seconds,
  val dnsLookupStrategy: DnsLookupStrategy = DnsLookupStrategy.ALL,
  val queriesPerResolvedAddress: Short = 3,
  val waitBetweenResolvedAddressQueries: Duration = 2.seconds,
  val ntpVersion: NTPVersion = NTPVersion.FOUR,
)
