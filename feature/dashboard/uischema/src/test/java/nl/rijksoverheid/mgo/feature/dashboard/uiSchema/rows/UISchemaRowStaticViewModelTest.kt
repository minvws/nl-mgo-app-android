package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.pft.Pft
import nl.rijksoverheid.mgo.data.pft.PftRepository
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UISchemaRowStaticViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @get:Rule
  val testServerRule = TestServerRule()

  private val testServer = testServerRule.testServer

  private lateinit var pftRepository: PftRepository

  @Before
  fun setup() {
    // Create pft repository
    pftRepository = PftRepository(okHttpClient = OkHttpClient(), url = testServer.url())

    // Sync with local pft.json file
    val pftJson = readResourceFile("pft.json")
    testServer.enqueueJson(pftJson)
    pftRepository.sync()
  }

  @Test
  fun testSnomedFound() =
    runTest {
      // Given: ViewModel with snomed 111002
      val viewModel =
        UISchemaRowStaticViewModel(
          snomedCode = "111002",
          pftRepository = pftRepository,
          ioDispatcher = mainDispatcherRule.testDispatcher,
        )

      // When: Observing pft
      viewModel.pft.test {
        // Then: Pft is returned
        val expectedPvt =
          Pft(
            description = "Kleine klieren die achter of naast de schildklier liggen en het hormoon PTH (parathyroÃ¯dhormoon) produceren.",
            synonym = "bijschildklier",
            name = "structuur van bijschildklier (lichaamsstructuur)",
          )
        assertEquals(expectedPvt, awaitItem())
      }
    }

  @Test
  fun testSnomedNotFound() =
    runTest {
      // Given: ViewModel with snomed 111002
      val viewModel =
        UISchemaRowStaticViewModel(
          snomedCode = "123",
          pftRepository = pftRepository,
          ioDispatcher = mainDispatcherRule.testDispatcher,
        )

      // When: Observing pft
      viewModel.pft.test {
        // Then: No pft is returned
        assertNull(awaitItem())
      }
    }
}
