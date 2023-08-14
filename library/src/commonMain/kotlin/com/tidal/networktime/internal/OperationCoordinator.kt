package com.tidal.networktime.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.time.Duration

internal class OperationCoordinator
@OptIn(ExperimentalCoroutinesApi::class)
constructor(
  private val mutableState: MutableState,
  private val coroutineScope: CoroutineScope,
  globalDispatcher: CoroutineDispatcher,
  private val syncInterval: Duration,
  private val toggleDispatcher: CoroutineDispatcher = globalDispatcher.limitedParallelism(1),
  private val syncDispatcher: CoroutineDispatcher = globalDispatcher.limitedParallelism(1),
) {

  fun dispatchStartSync() = dispatch(
    SyncEnable(mutableState, coroutineScope, syncDispatcher, syncInterval),
  )

  fun dispatchStopSync() = dispatch(SyncDisable(mutableState))

  private fun dispatch(block: () -> Unit) = coroutineScope.launch(toggleDispatcher) { block() }
}
