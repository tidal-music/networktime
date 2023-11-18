package root

import com.tidal.networktime.NTPServer
import com.tidal.networktime.SNTPClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.properties.Delegates
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class MainViewModel {
  private val sntpClient = SNTPClient(
    NTPServer(
      "time.google.com",
      queriesPerResolvedAddress = 1,
      waitBetweenResolvedAddressQueries = 1.seconds,
    ),
    synchronizationInterval = 5.seconds,
    backupFilePath = "sampleClockBackupFile",
  )
  private val stateCalculator = StateCalculator(sntpClient)
  private var synchronizationEnabled by Delegates.observable(false) { _, _, _ ->
    publishState()
  }
  private val _uiState = MutableStateFlow(calculateState())
  val uiState = _uiState.asStateFlow()

  init {
    toggleSynchronization()
    GlobalScope.launch {
      while (true) {
        publishState()
        delay(1.milliseconds)
      }
    }
  }

  fun toggleSynchronization() {
    when (synchronizationEnabled) {
      true -> sntpClient.disableSynchronization()
      false -> sntpClient.enableSynchronization()
    }
    synchronizationEnabled = !synchronizationEnabled
  }

  private fun calculateState() = stateCalculator(synchronizationEnabled)

  private fun publishState() {
    _uiState.update { calculateState() }
  }
}
