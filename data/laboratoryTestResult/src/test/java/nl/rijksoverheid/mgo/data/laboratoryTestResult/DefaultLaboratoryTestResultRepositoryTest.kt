package nl.rijksoverheid.mgo.data.laboratoryTestResult

import nl.rijksoverheid.mgo.data.api.dva.createDvaApi
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServerRule
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.MgoLaboratoryTestResult

internal class DefaultLaboratoryTestResultRepositoryTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer

    @Test
    fun `Given valid request, When getting concerns, Then return expected objects`() =
        runTest {
            // Given
            testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "observation_input.json"))

            // When
            val repository = getRepository()
            val result = repository.getLaboratoryTestResults()

            // Then
            assertTrue(result.getOrNull()?.firstOrNull() is MgoLaboratoryTestResult)
        }

    @Test
    fun `Given request errors, When getting concerns, Then return error`() =
        runTest {
            // Given
            testServer.enqueue500()

            // When
            val repository = getRepository()
            val result = repository.getLaboratoryTestResults()

            // Then
            assertEquals(false, result.isSuccess)
            val exception = result.exceptionOrNull() as? HttpException
            assertEquals(500, exception?.code())
        }

    private fun getRepository(): DefaultLaboratoryTestResultRepository {
        val okHttpClient = TEST_OKHTTP_CLIENT
        val dvaApi = createDvaApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
        return DefaultLaboratoryTestResultRepository(dvaApi = dvaApi)
    }
}
