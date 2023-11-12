package com.tidal.networktime

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Describes a host name that can resolve to any number of NTP unicast servers.
 *
 * @param queryTimeout The timeout for receiving responses from each of the servers resolved from
 * [name].
 * @param addressFamilies Can be used for filtering addresses resolved from [name] based on address
 * family.
 * @param queriesPerResolvedAddress The amount of queries to perform to each resolved address. More
 * queries may or may not increase precision, but they will make synchronization take longer and
 * also cause more server load.
 * @param waitBetweenResolvedAddressQueries The amount of time to wait before consecutive requests
 * to the same resolved address.
 * @param ntpVersion The version number to write in packets.
 */
class NTPServer(
  val name: String,
  val queryTimeout: Duration = 5.seconds,
  vararg val addressFamilies: AddressFamily = arrayOf(AddressFamily.INET),
  val queriesPerResolvedAddress: Short = 3,
  val waitBetweenResolvedAddressQueries: Duration = 2.seconds,
  val ntpVersion: NTPVersion = NTPVersion.FOUR,
)
