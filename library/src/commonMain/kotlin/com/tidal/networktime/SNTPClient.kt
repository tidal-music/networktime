package com.tidal.networktime

import com.tidal.networktime.internal.SNTPClientImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Construct a new SNTP client that can be requested to periodically interact with the provided
 * [ntpServers] to obtain information about their provided time.
 *
 * @param ntpServers Representation of supported unicast NTP sources.
 * @param referenceClock A provider of UNIX time used to calculate timing differences with the
 * information obtained from [ntpServers].
 * @param coroutineScope The scope where synchronization will run on.
 * @param synchronizationInterval The amount of time to wait between a sync finishing and the next
 * one being started.
 */
class SNTPClient(
  vararg val ntpServers: NTPServer,
  val referenceClock: ReadableClock,
  val coroutineScope: CoroutineScope = GlobalScope,
  val synchronizationInterval: Duration = 64.seconds,
) {
  private val delegate = SNTPClientImpl(
    ntpServers,
    referenceClock,
    coroutineScope,
    synchronizationInterval,
  )

  /**
   * The calculated epoch time if it has been calculated at least once or null otherwise. Accuracy
   * may vary depending on time since the last successful synchronization and accuracy of the
   * provided [referenceClock].
   */
  val epochTime by delegate::epochTime

  /**
   * Starts periodic synchronization. If it's already started, it does nothing. Otherwise, it
   * requests an immediate dispatch of a synchronization and subsequent ones
   * [synchronizationInterval] after each other.
   *
   * @return The [Job] for the task that will run the requested synchronization activity update.
   */
  fun enableSynchronization() = delegate.enableSynchronization()

  /**
   * Stops periodic synchronization if already started, does nothing otherwise. Safe to call
   * repeatedly.
   *
   * @return The [Job] for the task that will run the requested synchronization activity update.
   */
  fun disableSynchronization() = delegate.disableSynchronization()
}
