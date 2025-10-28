package nl.rijksoverheid.mgo.data.pft

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PftRepositoryTest {
  @get:Rule
  val testServerRule = TestServerRule()

  private val testServer = testServerRule.testServer

  private fun getRepository() =
    PftRepository(
      okHttpClient = OkHttpClient(),
      url = testServer.url(),
    )

  @Test
  fun testSyncSuccess() =
    runTest {
      // Given: Repository
      val repository = getRepository()

      // Given: Request success
      val pftJson = readResourceFile("pft.json")
      testServer.enqueueJson(pftJson)

      // When: Calling sync
      repository.sync()

      // Then: Observing existing pft snomed code returns pft
      repository.observe(PftSnomedCode("111002")).test {
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
  fun testSyncFailed() =
    runTest {
      // Given: Repository
      val repository = getRepository()

      // Given: Request failed
      testServer.enqueue500()

      // When: Calling sync
      repository.sync()

      // Then: Observing existing pft snomed code returns nothing
      repository.observe(PftSnomedCode("111002")).test {
        expectNoEvents()
      }
    }
}
