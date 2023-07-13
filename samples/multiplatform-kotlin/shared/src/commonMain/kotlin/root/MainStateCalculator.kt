package root

import com.tidal.networktime.SNTPClient
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.milliseconds

internal class MainStateCalculator(
  private val sntpClient: SNTPClient,
  private val localClock: Clock = Clock.System,
) {
  operator fun invoke(synchronizationEnabled: Boolean): MainState = MainState(
    localEpoch = localClock.now().toEpochMilliseconds().milliseconds,
    synchronizedEpoch = sntpClient.epochTime,
    synchronizationEnabled,
  )
}
