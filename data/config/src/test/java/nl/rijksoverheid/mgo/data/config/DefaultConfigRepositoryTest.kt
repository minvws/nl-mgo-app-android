package nl.rijksoverheid.mgo.data.config

import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class DefaultConfigRepositoryTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer

    @Test
    fun `Given config has lower app version than app, When refreshing the config, Then NoAction required is returned`() {
        runTest {
            // Given (config has minimum app version of 1000)
            testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "response/config.json"))

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
            testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "response/config.json"))

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
        val environmentRepository = TestEnvironmentRepository()
        environmentRepository.setEnvironment(Environment.Tst(versionCode = appVersion))
        return DefaultConfigRepository(environmentRepository = environmentRepository, configApi = configApi)
    }
}
