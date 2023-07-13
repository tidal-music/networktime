package com.tidal.networktime.singletons.internal

import com.tidal.networktime.SNTPClient

internal val SNTPClient.singletonImpl: SNTPClient
  get() = SingletonSNTPClientMap.getOrAdd(this)
