package root

import androidx.compose.ui.window.singleWindowApplication

internal class Main {
  companion object {
    private val VIEWMODEL = MainViewModel()

    @JvmStatic
    fun main(vararg args: String) = singleWindowApplication(
      title = "JVM sample",
      resizable = false,
    ) {
      MainScreen(VIEWMODEL)
    }
  }
}
