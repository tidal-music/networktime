package com.tidal.networktime.internal

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.js.Js

internal actual class HttpClientFactory {

  actual operator fun invoke(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Js, config)
}
