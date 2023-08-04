package com.tidal.networktime

import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Construct a new SNTP client that can be requested to periodically interact with the provided
 * [ntpServers] to obtain information about their provided time.
 *
 * @param ntpServers Representation of supported unicast NTP sources.
 * @param coroutineScope The scope where synchronization will run on.
 * @param referenceClock A provider of UNIX time, used to calculate timing differences with the
 * information obtained from [ntpServers].
 * @param syncInterval The amount of time to wait between a sync finishing and the next one being
 * started.
 */
class SNTPClient(
  vararg val ntpServers: NTPServer,
  val coroutineScope: CoroutineScope,
  val referenceClock: () -> Duration,
  val syncInterval: Duration = 64.seconds,
) {

  val synchronizedEpochTime: Duration?
    get() = TODO("Getting the time")

  /**
   * Starts periodic synchronization. If it's already started, it does nothing. Otherwise, it
   * requests an immediate dispatch of a synchronization and subsequent ones [syncInterval] after
   * each other.
   */
  fun startSynchronization(): Unit = TODO("Start or return")

  /**
   * Stops periodic synchronization if already started, does nothing otherwise.
   */
  fun stopSynchronization(): Unit = TODO("Stop or return")
}
