package nl.rijksoverheid.mgo.rules

import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.MainApplication
import org.junit.rules.ExternalResource

class SetupAppRule(
  private val skipOnboarding: Boolean = false,
  private val pinCode: List<Int>? = null,
  private val digidAuthenticated: Boolean = false,
  private val skipPinCodeLogin: Boolean = false,
  private val mainApplication: MainApplication = ApplicationProvider.getApplicationContext(),
) : ExternalResource() {
  override fun before() =
    runTest {
      val appInitializer = mainApplication.appInitializer
      appInitializer.override(
        skipOnboarding = skipOnboarding,
        pinCode = pinCode,
        digidAuthenticated = digidAuthenticated,
        skipPinCodeLogin = skipPinCodeLogin,
      )
    }

  override fun after() =
    runTest {
      mainApplication.appInitializer.clear()
    }
}
