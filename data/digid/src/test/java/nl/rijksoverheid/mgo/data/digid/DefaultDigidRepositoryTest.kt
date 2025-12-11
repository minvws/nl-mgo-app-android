package nl.rijksoverheid.mgo.data.digid

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Assert.assertTrue
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
        json = readResourceFile("start_response.json"),
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
      assertTrue(result.isFailure)
    }

  private fun getRepository(): DefaultDigidRepository =
    DefaultDigidRepository(
      okHttpClient = OkHttpClient(),
      baseUrl = testServer.url(),
      environmentRepository = TestEnvironmentRepository(),
    )
}
