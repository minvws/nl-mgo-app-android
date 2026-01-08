package nl.rijksoverheid.mgo.feature.settings.about.home.versions

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.pft.PftRepository
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import nl.rijksoverheid.mgo.framework.util.base64.file.ReadLocalFileFromResources
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsHomeVersionsScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @get:Rule
  val testServerRule = TestServerRule()

  private lateinit var pftRepository: PftRepository
  private lateinit var viewModel: SettingsHomeVersionsScreenViewModel

  @Before
  fun setup() =
    runTest {
      pftRepository = PftRepository(okHttpClient = OkHttpClient(), url = testServerRule.testServer.url())
      viewModel =
        SettingsHomeVersionsScreenViewModel(
          ioDispatcher = mainDispatcherRule.testDispatcher,
          pftRepository = pftRepository,
          readLocalFile = ReadLocalFileFromResources(),
        )
    }

  @Test
  fun testViewState() =
    runTest {
      // Given: Pft call returns a response with ETag header
      testServerRule.testServer.enqueueJson(
        json = "{}",
        headers =
          listOf(
            Pair("ETag", "123"),
          ),
      )
      pftRepository.sync()

      // When: Observing view state
      viewModel.viewState.test {
        // Then: Expect view state
        val viewState = awaitItem()
        assertEquals("main", viewState.hcimPackageVersion)
        assertEquals("d76a21d", viewState.hcimPackageGitRef)
        assertEquals("2025-12-10T09:29:42", viewState.hcimPackageDate)
        assertEquals("main", viewState.healthCategoriesConfigVersion)
        assertEquals("f23a72e", viewState.healthCategoriesConfigGitRef)
        assertEquals("2025-12-08T15:22:46", viewState.healthCategoriesConfigDate)
        assertEquals("123", viewState.patientFriendlyTermsETag)
      }
    }
}
