package root

import kotlin.time.Duration

data class MainState(
  val referenceEpoch: Duration,
  val synchronizedEpoch: Duration?,
)
