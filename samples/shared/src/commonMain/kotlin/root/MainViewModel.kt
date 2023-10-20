package root

import com.tidal.networktime.DnsLookupStrategy
import com.tidal.networktime.NTPServer
import com.tidal.networktime.SNTPClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainViewModel {
  private val sntpClient = SNTPClient(
    NTPServer("time.google.com"),
    NTPServer("time.apple.com", dnsLookupStrategy = DnsLookupStrategy.IP_V6),
  )
  private val stateCalculator = StateCalculator(sntpClient)
  private val _uiState = MutableStateFlow(stateCalculator())
  val uiState = _uiState.asStateFlow()

  init {
    sntpClient.enableSynchronization()
    GlobalScope.launch {
      while (true) {
        _uiState.update { stateCalculator() }
        delay(1.milliseconds)
      }
    }
  }
}
