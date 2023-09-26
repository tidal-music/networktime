package root

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel {
  private val stateCalculator = StateCalculator()
  private val _uiState = MutableStateFlow(stateCalculator())
  val uiState = _uiState.asStateFlow()
}
