package nl.rijksoverheid.mgo.rules

import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.MainActivity
import nl.rijksoverheid.mgo.MainApplication
import org.junit.rules.TestWatcher

class LaunchAppRule(
  private val mainApplication: MainApplication =
    ApplicationProvider.getApplicationContext(),
) : TestWatcher() {
  fun launchApp(
    clearOrganizations: Boolean = true,
    skipOnboarding: Boolean = false,
    pinCode: List<Int>? = null,
    digidAuthenticated: Boolean = false,
    skipPinCodeLogin: Boolean = false,
    block: () -> Unit,
  ) = runTest {
    val appInitializer = mainApplication.appInitializer
    appInitializer.override(
      clearOrganizations = clearOrganizations,
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
