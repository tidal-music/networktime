package com.tidal.networktime

import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration

/**
 * Construct a new SNTP client that can be requested to periodically interact with the provided
 * [ntpServers] to obtain information about their provided time.
 *
 * @param ntpServers Representation of supported unicast NTP sources.
 * @param coroutineScope The scope where synchronization will run on.
 * @param referenceClock A provider of UNIX time, used to calculate timing differences with the
 * information obtained from [ntpServers].
 */
class SNTPClient(
  vararg val ntpServers: NTPServer,
  val coroutineScope: CoroutineScope,
  val referenceClock: () -> Duration,
) {

  val time: Long?
    get() = TODO("Getting the time")

  fun startSynchronization(): Unit = TODO("Start or return")

  fun stopSynchronization(): Unit = TODO("Stop or return")
}
