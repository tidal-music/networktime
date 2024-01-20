package root

import androidx.compose.ui.window.singleWindowApplication

private val VIEWMODEL = MainViewModel()

internal fun main() = singleWindowApplication(title = "Desktop sample") {
  MainScreen(VIEWMODEL)
}
