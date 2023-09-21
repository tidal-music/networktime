package root

import kotlin.time.Duration

internal data class MainState(
  val referenceEpoch: Duration,
  val networkEpoch: Duration,
)
