package nl.rijksoverheid.mgo.data.api.dva

import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServerRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

class DvaApiTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer

    @Test
    fun `Given response with empty string, when calling medicationStatement, return response with empty string`() =
        runTest {
            // Given
            testServer.enqueue200()

            // When
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            val responseBody = dvaApi.medicationStatement("")

            // Then
            assertEquals("", responseBody.string())
        }

    @Test
    fun `Given response with empty string, when calling condition, return response with empty string`() =
        runTest {
            // Given
            testServer.enqueue200()

            // When
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            val responseBody = dvaApi.condition("")

            // Then
            assertEquals("", responseBody.string())
        }

    @Test
    fun `Given response with empty string, when calling observation, return response with empty string`() =
        runTest {
            // Given
            testServer.enqueue200()

            // When
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            val responseBody = dvaApi.observation("", "")

            // Then
            assertEquals("", responseBody.string())
        }
}
