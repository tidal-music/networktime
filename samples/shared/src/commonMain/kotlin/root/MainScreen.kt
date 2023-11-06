package root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel) {
  val state = mainViewModel.uiState.collectAsState().value
  val textStyle = MaterialTheme.typography.bodyLarge.copy(fontFeatureSettings = "tnum")
  MaterialTheme {
    Scaffold { paddingValues ->
      FlowRow(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(paddingValues).fillMaxSize(),
      ) {
        FlowColumn(
          verticalArrangement = Arrangement.SpaceAround,
          horizontalArrangement = Arrangement.Start,
        ) {
          Text("Local: ", style = textStyle)
          Text("Synchronized: ", style = textStyle)
        }
        FlowColumn(
          verticalArrangement = Arrangement.SpaceAround,
          horizontalArrangement = Arrangement.Start,
        ) {
          Text(state.localEpoch.epochToString, style = textStyle)
          val synchronizedEpoch = state.synchronizedEpoch ?: return@FlowColumn
          val diff = synchronizedEpoch - state.localEpoch
          Text(
            "${synchronizedEpoch.epochToString} " +
              "(${
                if (diff > Duration.ZERO) {
                  "ahead "
                } else if (diff < Duration.ZERO) {
                  "behind "
                } else {
                  ""
                }
              }$diff)",
            style = textStyle,
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
