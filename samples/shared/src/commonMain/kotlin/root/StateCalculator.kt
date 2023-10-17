package root

import com.tidal.networktime.ReadableClock
import com.tidal.networktime.SNTPClient

internal class StateCalculator(
  private val referenceClock: ReadableClock,
  private val sntpClient: SNTPClient,
) {
  operator fun invoke(): MainState = MainState(
    referenceEpoch = referenceClock.epochTime,
    synchronizedEpoch = sntpClient.epochTime,
  )
}
