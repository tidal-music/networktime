package root

import com.tidal.networktime.ReadableClock
import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

internal class KotlinXDateTimeSystemClock : ReadableClock {
  override val epochTime: Duration
    get() = Clock.System.now().toEpochMilliseconds().milliseconds
}
