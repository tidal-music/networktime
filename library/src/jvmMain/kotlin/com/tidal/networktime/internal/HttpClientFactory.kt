package com.tidal.networktime.internal

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp

internal actual class HttpClientFactory {

  actual operator fun invoke(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp, config)
}
