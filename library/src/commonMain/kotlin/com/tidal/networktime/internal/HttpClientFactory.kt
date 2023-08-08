package com.tidal.networktime.internal

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

internal expect class HttpClientFactory() {

  operator fun invoke(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient
}
