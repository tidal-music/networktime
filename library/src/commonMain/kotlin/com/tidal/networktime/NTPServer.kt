package com.tidal.networktime

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

sealed interface NTPServer {
  val hostName: String
  val responseTimeout: Duration
  val dnsResolutionStrategy: DNSResolutionStrategy

  sealed interface Unicast : NTPServer {
    class Sequential(
      override val hostName: String,
      override val responseTimeout: Duration = 5.seconds,
      override val dnsResolutionStrategy: DNSResolutionStrategy = DNSResolutionStrategy.ALL,
      val outgoingRequestGap: Duration = OUTGOING_REQUEST_GAP_DEFAULT_SECONDS.seconds,
    ) : Unicast

    class Concurrent(
      override val hostName: String,
      override val responseTimeout: Duration = 5.seconds,
      override val dnsResolutionStrategy: DNSResolutionStrategy = DNSResolutionStrategy.ALL,
    ) : Unicast

    companion object {
      private const val OUTGOING_REQUEST_GAP_DEFAULT_SECONDS = 2
    }
  }

  class Anycast(
    override val hostName: String,
    override val responseTimeout: Duration = 68.seconds,
    override val dnsResolutionStrategy: DNSResolutionStrategy = DNSResolutionStrategy.ALL,
  ) : NTPServer

  enum class DNSResolutionStrategy {
    IP_V4,
    IP_V6,
    ALL,
  }
}
