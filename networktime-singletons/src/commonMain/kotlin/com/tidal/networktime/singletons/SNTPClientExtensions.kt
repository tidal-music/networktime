package com.tidal.networktime.singletons

import com.tidal.networktime.SNTPClient
import com.tidal.networktime.singletons.internal.singletonImpl

val SNTPClient.singleton: SNTPClient
  get() = singletonImpl
