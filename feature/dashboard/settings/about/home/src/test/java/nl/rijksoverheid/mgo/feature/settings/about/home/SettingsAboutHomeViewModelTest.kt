package nl.rijksoverheid.mgo.feature.settings.about.home

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal class SettingsAboutHomeViewModelTest {
  @Test
  fun testViewState() =
    runTest {
      // Given: ViewModel
      val viewModel =
        SettingsAboutHomeViewModel(
          versionCode = 1,
          versionName = "1.0.0",
          environmentRepository = TestEnvironmentRepository(),
        )

      // Then: Expected view state
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(1, viewState.appVersionCode)
        assertEquals("1.0.0", viewState.appVersionName)
        assertEquals(CopyR.string.privacy_link_test, viewState.privacyUrl)
      }
    }
}
