package nl.rijksoverheid.mgo.robots

import androidx.test.core.app.launchActivity
import kotlinx.coroutines.coroutineScope
import nl.rijksoverheid.mgo.MainActivity
import nl.rijksoverheid.mgo.MainApplication

class LaunchAppRobot(
  private val mainApplication: MainApplication,
) {
  suspend fun launchApp(
    skipOnboarding: Boolean = false,
    pinCode: List<Int>? = null,
    digidAuthenticated: Boolean = false,
    skipPinCodeLogin: Boolean = false,
    block: () -> Unit,
  ) = coroutineScope {
    val appInitializer = mainApplication.appInitializer
    appInitializer.override(
      skipOnboarding = skipOnboarding,
      pinCode = pinCode,
      digidAuthenticated = digidAuthenticated,
      skipPinCodeLogin = skipPinCodeLogin,
    )

    launchActivity<MainActivity>().use {
      block()
    }
  }
}
