package nl.rijksoverheid.mgo.data.healthData.fhir

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.api.dva.createDvaApi
import nl.rijksoverheid.mgo.data.healthData.configuration.models.TEST_ENDPOINT
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
      // Given: Request returns json
      testServer.enqueueJson("{}")

      // When: Calling fetch
      val repository = getRepository()
      val result = repository.fetch(endpoint = TEST_ENDPOINT, fhirVersion = "3.0")

      // Then: Return success result
      assertTrue(result.isSuccess)
    }

  @Test
  fun testFetchFailed() =
    runTest {
      // Given: Request returns error
      testServer.enqueue500()

      // When: Calling fetch
      val repository = getRepository()
      val result = repository.fetch(endpoint = TEST_ENDPOINT, fhirVersion = "3.0")

      // Then: Return success result
      assertTrue(result.isFailure)
    }

  private fun getRepository(): DefaultFhirDataRepository =
    DefaultFhirDataRepository(
      dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url()),
      dvaApiBaseUrl = "",
      cacheFileStore = cacheFileStore,
      base64Util = TestBase64Util(),
    )
}
