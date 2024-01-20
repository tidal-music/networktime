package root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
  val state = mainViewModel.uiState.collectAsState().value
  val textStyle = MaterialTheme.typography.bodyLarge.copy(fontFeatureSettings = "tnum")
  MaterialTheme {
    Scaffold { _ ->
      Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(12.dp).fillMaxSize(),
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Text("Sys=", style = textStyle)
          Text(state.localEpoch.epochToString, style = textStyle)
        }
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth(),
        ) {
          val synchronizedEpoch = state.synchronizedEpoch
          val deltaString = if (synchronizedEpoch == null) {
            "N/A"
          } else {
            synchronizedEpoch - state.localEpoch
          }
          Text("Net (δ=$deltaString)=", style = textStyle)
          Text(synchronizedEpoch?.epochToString ?: "Not yet available", style = textStyle)
        }
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceEvenly,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Text("Synchronization ${if (state.synchronizationEnabled) "enabled" else "disabled"}")
          Switch(
            checked = state.synchronizationEnabled,
            onCheckedChange = { mainViewModel.toggleSynchronization() },
          )
        }
      }
    }
  }
}

private val Duration.epochToString
  get() = Instant.fromEpochMilliseconds(inWholeMilliseconds)
    .toLocalDateTime(TimeZone.UTC)
    .toString()
