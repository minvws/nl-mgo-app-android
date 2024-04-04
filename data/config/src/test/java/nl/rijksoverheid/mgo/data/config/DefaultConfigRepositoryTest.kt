package nl.rijksoverheid.mgo.data.config

import nl.rijksoverheid.mgo.framework.environment.AppInfo
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServer
import nl.rijksoverheid.mgo.framework.test.loadJsonFromResources
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class DefaultConfigRepositoryTest {
    private val testServer = TestServer()

    @Test
    fun `Given config has lower app version than app, When refreshing the config, Then NoAction required is returned`() {
        runTest {
            // Given (config has minimum app version of 1000)
            testServer.start()
            testServer.enqueue(MockResponse().setBody(javaClass.loadJsonFromResources(filePath = "response/config.json")))

            // When
            val repository = getRepository(appVersion = 1001)
            val result = repository.refresh()

            // Then
            val expectedConfig = ConfigState.NoAction
            assertEquals(Result.success(expectedConfig), result)
        }
    }

    @Test
    fun `Given config has higher app version than app, When refreshing the config, Then UpdatedRequired is returned`() {
        runTest {
            // Given (config has minimum app version of 1000)
            testServer.start()
            testServer.enqueue(MockResponse().setBody(javaClass.loadJsonFromResources(filePath = "response/config.json")))

            // When
            val repository = getRepository(appVersion = 999)
            val result = repository.refresh()

            // Then
            val expectedConfig = ConfigState.UpdateRequired
            assertEquals(Result.success(expectedConfig), result)
        }
    }

    private fun getRepository(appVersion: Int): DefaultConfigRepository {
        val okHttpClient = TEST_OKHTTP_CLIENT
        val configApi = createApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
        return DefaultConfigRepository(appInfo = AppInfo(appVersion), configApi = configApi)
    }
}
