package root

import android.app.Application

internal class MainApplication : Application() {

  val viewModel by lazy { MainViewModel() }
}
