package com.tidal.networktime

import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Construct a new SNTP client that can be requested to periodically interact with the provided
 * [ntpServers] to obtain information about their provided time.
 *
 * @param ntpServers Representation of supported NTP sources.
 * @param coroutineScope The scope where synchronization will run on.
 * @param ntpVersion The version number to write in packets.
 * @param portSelectionStrategy The strategy for selecting a port to operate on.
 * @param minimumSynchronizationInterval The minimum amount of time between performing time queries
 * on all unicast sources. The actual value used may be larger than this on occasion based on
 * changes to the difference between the time provided by [ntpServers] and [referenceClock] (if it
 * ever changes), but will never be lower than this value.
 * @param referenceClock A clock used to calculate timing differences with the information obtained
 * from [ntpServers]. This clock will never be modified directly.
 */
class SNTPClient(
  vararg val ntpServers: NTPServer,
  val coroutineScope: CoroutineScope,
  val ntpVersion: NTPVersion = NTPVersion.FOUR,
  val portSelectionStrategy: PortSelectionStrategy = PortSelectionStrategy.RFC9109,
  val minimumSynchronizationInterval: Duration = 64.seconds,
  val referenceClock: () -> Long,
) {

  val time: Long?
    get() = TODO("Getting the time")

  fun startSynchronization(): Unit = TODO("Start or return")

  fun stopSynchronization(): Unit = TODO("Stop or return")

  enum class NTPVersion(val descriptor: Short) {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
  }

  sealed class PortSelectionStrategy(val pinnedPortNumber: Int?) {
    data object RFC5905 : PortSelectionStrategy(123)

    /**
     * Make a new selection of a random available port every time a socket is opened.
     */
    data object RFC9109 : PortSelectionStrategy(null)
  }
}
