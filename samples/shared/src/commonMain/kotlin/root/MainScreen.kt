package root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
  val state = mainViewModel.uiState.collectAsState().value
}
