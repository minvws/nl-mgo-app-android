package nl.rijksoverheid.mgo.feature.settings.about.home

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.fhirParser.version.TestGetFhirParserVersion
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

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
                )

            // Then: Expected view state
            val expectedFhirVersion =
                "{ \"version\": \"main\", \"git_ref\": \"d2c2081aefcaa7c0e8c413a1b8c654bcdcbe7705\", \"created\": \"2025-03-21T16:01:38\"}"
            viewModel.viewState.test {
                val viewState = awaitItem()
                assertEquals(1, viewState.appVersionCode)
                assertEquals("1.0.0", viewState.appVersionName)
                assertEquals(expectedFhirVersion, viewState.fhirParserVersion)
            }
        }
}
