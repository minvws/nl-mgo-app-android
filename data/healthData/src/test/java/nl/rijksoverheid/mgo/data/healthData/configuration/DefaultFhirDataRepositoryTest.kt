package nl.rijksoverheid.mgo.data.healthData.configuration

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.api.dva.createDvaApi
import nl.rijksoverheid.mgo.data.healthData.configuration.models.TEST_ENDPOINT
import nl.rijksoverheid.mgo.data.healthData.fhir.DefaultFhirDataRepository
import nl.rijksoverheid.mgo.data.healthData.fhir.models.JsonResponseState
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestCacheFileStore
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import nl.rijksoverheid.mgo.framework.util.base64.TestBase64Util
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DefaultFhirDataRepositoryTest {
  @get:Rule
  val testServerRule = TestServerRule()

  private val testServer = testServerRule.testServer

  private val cacheFileStore = TestCacheFileStore()

  @Test
  fun testFetchSuccess() =
    runTest {
      // Given: Request returns error
      testServer.enqueueJson("{}")

      // When: Calling fetch
      val repository = getRepository()
      repository
        .fetch(
          endpoint = TEST_ENDPOINT,
          fhirVersion = "3.0",
        ).test {
          assertTrue(awaitItem() is JsonResponseState.Loading)
          assertTrue(awaitItem() is JsonResponseState.Success)
          assertTrue(cacheFileStore.assertFileSaved())
          awaitComplete()
        }
    }

  @Test
  fun testFetchFailed() =
    runTest {
      // Given: Request returns error
      testServer.enqueue500()

      // When: Calling fetch
      val repository = getRepository()
      repository
        .fetch(
          endpoint = TEST_ENDPOINT,
          fhirVersion = "3.0",
        ).test {
          assertTrue(awaitItem() is JsonResponseState.Loading)
          assertTrue(awaitItem() is JsonResponseState.Error)
          awaitComplete()
        }
    }

  private fun getRepository(): DefaultFhirDataRepository =
    DefaultFhirDataRepository(
      dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url()),
      dvaApiBaseUrl = "",
      cacheFileStore = cacheFileStore,
      base64Util = TestBase64Util(),
    )
}
