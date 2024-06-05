package nl.rijksoverheid.mgo.data.api.dva

import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServerRule
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

class FhirConverterFactoryTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer

    @Test
    fun `Given response has medication statement, When getting medication statement, Response is converted`() =
        runTest {
            // Given
            testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "response/medicationstatement.json"))

            // When
            val dvaApi = getDvaApi()
            val medicationStatement = dvaApi.medicationStatement()

            // Then
            assertEquals(1, medicationStatement.size)
        }

    private fun getDvaApi(): DvaApi {
        val okHttpClient = TEST_OKHTTP_CLIENT
        return createDvaApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
    }
}
