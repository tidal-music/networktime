package com.tidal.networktime.internal

import com.tidal.networktime.NTPServer
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
  private val domainNameResolver: DomainNameResolver,
  private val ntpServers: Iterable<NTPServer>,
  private val toggleDispatcher: CoroutineDispatcher = globalDispatcher.limitedParallelism(1),
  private val syncDispatcher: CoroutineDispatcher = globalDispatcher,
) {
  fun dispatchStartSync() = dispatch(
    SyncEnable(
      mutableState,
      coroutineScope,
      syncDispatcher,
      syncInterval,
      domainNameResolver,
      ntpServers,
    ),
  )

  fun dispatchStopSync() = dispatch(SyncDisable(mutableState))

  private fun dispatch(block: () -> Unit) = coroutineScope.launch(toggleDispatcher) { block() }
}
