package root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection

internal class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Scaffold { paddingValues ->
        Column(
          modifier = Modifier.padding(
            PaddingValues(
              start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
              top = paddingValues.calculateTopPadding(),
              end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
              bottom = paddingValues.calculateBottomPadding(),
            ),
          ),
        ) {
          MainScreen((application as MainApplication).viewModel)
        }
      }
    }
  }
}
