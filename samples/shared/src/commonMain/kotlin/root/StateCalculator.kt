package root

import com.tidal.networktime.SNTPClient
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.milliseconds

internal class StateCalculator(
  private val sntpClient: SNTPClient,
  private val localClock: Clock = Clock.System,
) {
  operator fun invoke(): MainState = MainState(
    localEpoch = localClock.now().toEpochMilliseconds().milliseconds,
    synchronizedEpoch = sntpClient.epochTime,
  )
}
