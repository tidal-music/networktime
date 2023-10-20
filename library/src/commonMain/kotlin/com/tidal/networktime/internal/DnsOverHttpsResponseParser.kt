package com.tidal.networktime.internal

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

internal class DnsOverHttpsResponseParser {
  operator fun invoke(response: String): Iterable<String> {
    val responseAsJson = try {
      Json.parseToJsonElement(response).jsonObject
    } catch (_: SerializationException) {
      return emptySet()
    }
    val requestedTypes = responseAsJson["Question"]?.jsonArray?.mapNotNull { it.jsonObject["type"] }
      ?: return emptySet()
    return responseAsJson["Answer"]?.jsonArray
      ?.filter { it.jsonObject["type"] in requestedTypes }
      ?.map { it.jsonObject["data"]!!.jsonPrimitive.content }
      ?: emptySet()
  }
}
