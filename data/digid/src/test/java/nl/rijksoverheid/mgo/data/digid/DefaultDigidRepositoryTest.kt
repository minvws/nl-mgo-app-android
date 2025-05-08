package nl.rijksoverheid.mgo.data.digid

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.api.vad.createVadApi
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class DefaultDigidRepositoryTest {
  @get:Rule
  val testServerRule = TestServerRule()
  private val testServer = testServerRule.testServer

  @Test
  fun testLoginSuccess() =
    runTest {
      // Given: successful request
      testServer.enqueueJson(
        json = getTestServerBodyForUnitTest(filePath = "response/start_response.json"),
      )

      // When: calling login
      val repository = getRepository()
      val result = repository.login()

      // Then: url is returned
      val url = "https://www.google.com"
      Assert.assertEquals(Result.success(url), result)
    }

  @Test
  fun testLoginFailure() =
    runTest {
      // Given: failed request
      testServer.enqueue500()

      // When: calling login
      val repository = getRepository()
      val result = repository.login()

      // Then: failure is returned
      Assert.assertTrue(result.isFailure)
    }

  private fun getRepository(): DefaultDigidRepository {
    val okHttpClient = TEST_OKHTTP_CLIENT
    val vadApi = createVadApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
    return DefaultDigidRepository(
      vadApi = vadApi,
      environmentRepository = TestEnvironmentRepository(),
    )
  }
}
