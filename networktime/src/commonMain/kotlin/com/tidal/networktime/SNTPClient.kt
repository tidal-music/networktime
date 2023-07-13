package com.tidal.networktime

import com.tidal.networktime.internal.SNTPClientImpl
import kotlinx.coroutines.Job
import okio.Path.Companion.toPath
import kotlin.native.ObjCName
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Constructs a new SNTP client that can be requested to periodically interact with the provided
 * [ntpServers] to obtain information about their provided time.
 *
 * @param ntpServers Representation of supported unicast NTP sources.
 * @param synchronizationInterval The amount of time to wait between a sync finishing and the next
 * one being started.
 * @param backupFilePath A path to a file that will be used to save the selected received NTP
 * packets, as well as to read packets before one is available from the network. If `null` then
 * [epochTime] is guaranteed to return `null` from program start-up until at least one valid NTP
 * packet has been received and processed. If not `null` but writing or reading fail when attempted,
 * program execution will continue as if it had been `null` until the next attempt.
 */
@ObjCName(name = "TNTSNTPClient", swiftName = "SNTPClient", exact = true)
class SNTPClient(
  vararg val ntpServers: NTPServer,
  @ObjCName(name = "synchronizationIntervalMs")
  val synchronizationInterval: Duration = 64.seconds,
  val backupFilePath: String? = null,
) {
  private val delegate = SNTPClientImpl(
    ntpServers,
    backupFilePath?.toPath(),
    synchronizationInterval,
  )

  /**
   * The calculated epoch time if it has been calculated at least once or null otherwise.
   */
  @ObjCName("epochTimeMs")
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
