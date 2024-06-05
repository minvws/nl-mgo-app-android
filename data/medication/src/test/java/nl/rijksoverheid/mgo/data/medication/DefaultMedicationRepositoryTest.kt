package nl.rijksoverheid.mgo.data.medication

import nl.rijksoverheid.mgo.data.api.dva.createDvaApi
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServerRule
import nl.rijksoverheid.mgo.framework.test.getTestServerBodyForUnitTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import kotlinx.coroutines.test.runTest

internal class DefaultMedicationRepositoryTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer

    @Test
    fun `Given dvaApi request is successful, When calling getMedication, Then return medications`() =
        runTest {
            // Given
            testServer.enqueueJson(json = getTestServerBodyForUnitTest(filePath = "response/medicationstatement.json"))

            // When
            val repository = getRepository()
            val result = repository.getMedications()

            // Then
            assertEquals(true, result.isSuccess)
            assertEquals(1, result.getOrNull()?.size)
        }

    @Test
    fun `Given dvaApi request failed, When calling getMedication, Then return error`() =
        runTest {
            // Given
            testServer.enqueue500()

            // When
            val repository = getRepository()
            val result = repository.getMedications()

            // Then
            assertEquals(false, result.isSuccess)
            val exception = result.exceptionOrNull() as? HttpException
            assertEquals(500, exception?.code())
        }

    private fun getRepository(): DefaultMedicationRepository {
        val okHttpClient = TEST_OKHTTP_CLIENT
        val loadApi = createDvaApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
        return DefaultMedicationRepository(dvaApi = loadApi)
    }
}
