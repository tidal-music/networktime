import androidx.compose.ui.window.ComposeUIViewController
import root.MainScreen
import root.MainViewModel

private val VIEWMODEL = MainViewModel()

@Suppress("unused") // Used from sample iOS app XCode project
fun MainViewController() = ComposeUIViewController { MainScreen(VIEWMODEL) }
