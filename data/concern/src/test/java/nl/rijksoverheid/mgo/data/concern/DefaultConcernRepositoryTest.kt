package nl.rijksoverheid.mgo.data.concern

import nl.rijksoverheid.mgo.data.api.dva.createDvaApi
import nl.rijksoverheid.mgo.data.concern.models.MgoConcern
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import kotlinx.coroutines.test.runTest

internal class DefaultConcernRepositoryTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer

    @Test
    fun `Given valid request, When getting concerns, Then return expected objects`() =
        runTest {
            // Given
            testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "condition_input.json"))

            // When
            val repository = getRepository()
            val result = repository.getConcerns("")

            // Then
            assertTrue(result.getOrNull()?.firstOrNull() is MgoConcern)
        }

    @Test
    fun `Given request errors, When getting concerns, Then return error`() =
        runTest {
            // Given
            testServer.enqueue500()

            // When
            val repository = getRepository()
            val result = repository.getConcerns("")

            // Then
            assertEquals(false, result.isSuccess)
            val exception = result.exceptionOrNull() as? HttpException
            assertEquals(500, exception?.code())
        }

    private fun getRepository(): DefaultConcernRepository {
        val okHttpClient = TEST_OKHTTP_CLIENT
        val dvaApi = createDvaApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
        return DefaultConcernRepository(dvaApi = dvaApi)
    }
}
