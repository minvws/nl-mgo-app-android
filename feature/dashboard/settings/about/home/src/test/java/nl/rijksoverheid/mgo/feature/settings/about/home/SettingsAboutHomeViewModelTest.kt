package nl.rijksoverheid.mgo.feature.settings.about.home

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.version.TestGetFhirParserVersion
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
          getFhirParserVersion = TestGetFhirParserVersion(),
          environmentRepository = TestEnvironmentRepository(),
        )

      // Then: Expected view state
      val expectedFhirVersion =
        "{ \"version\": \"main\", \"git_ref\": \"d2c2081aefcaa7c0e8c413a1b8c654bcdcbe7705\", \"created\": \"2025-03-21T16:01:38\"}"
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(1, viewState.appVersionCode)
        assertEquals("1.0.0", viewState.appVersionName)
        assertEquals(expectedFhirVersion, viewState.fhirParserVersion)
        assertEquals(CopyR.string.settings_about_this_app_privacy_url_test, viewState.privacyUrl)
      }
    }
}
