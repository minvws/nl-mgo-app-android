package nl.rijksoverheid.mgo.data.config

import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServer
import nl.rijksoverheid.mgo.framework.test.loadJsonFromResources
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class DefaultConfigRepositoryTest {
    private val testServer = TestServer()

    @Test
    fun `Given a successful config fetch, When getting the config, return Config object`() =
        runTest {
            // Given
            testServer.start()
            testServer.enqueue(MockResponse().setBody(javaClass.loadJsonFromResources(filePath = "response/config.json")))

            // When
            val result = getRepository(baseUrl = testServer.url()).getConfig()

            // Then
            val expectedConfig = Config(androidMinimumVersion = 1, configTTL = 300, configMinimumIntervalSeconds = 60)
            assertEquals(Result.success(expectedConfig), result)
        }

    @Test
    fun `Given a failed config fetch, When getting the config, return failed`() =
        runTest {
            // Given
            testServer.start()
            testServer.enqueue(MockResponse().setResponseCode(404))

            // When
            val result = getRepository(baseUrl = testServer.url()).getConfig()

            // Then
            assertTrue(result.isFailure)
        }

    private fun getRepository(baseUrl: String): DefaultConfigRepository {
        val okHttpClient = TEST_OKHTTP_CLIENT
        val configApi = createApi(okHttpClient = okHttpClient, baseUrl = baseUrl)
        return DefaultConfigRepository(configApi = configApi)
    }
}
