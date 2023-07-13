package root

import kotlin.time.Duration

data class MainState(
  val localEpoch: Duration,
  val synchronizedEpoch: Duration?,
  val synchronizationEnabled: Boolean,
)
