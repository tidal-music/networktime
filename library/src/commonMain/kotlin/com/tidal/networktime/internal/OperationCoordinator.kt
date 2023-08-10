package com.tidal.networktime.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class OperationCoordinator
@OptIn(ExperimentalCoroutinesApi::class)
constructor(
  private val mutableState: MutableState,
  private val coroutineScope: CoroutineScope,
  globalDispatcher: CoroutineDispatcher,
  private val privateDispatcher: CoroutineDispatcher = globalDispatcher.limitedParallelism(1),
) {

  fun dispatchStartSync() = dispatch(StartSyncRunnable(mutableState))

  fun dispatchStopSync() = dispatch(StopSyncRunnable(mutableState))

  private fun dispatch(block: () -> Unit) = coroutineScope.launch {
    withContext(privateDispatcher) { block() }
  }
}
