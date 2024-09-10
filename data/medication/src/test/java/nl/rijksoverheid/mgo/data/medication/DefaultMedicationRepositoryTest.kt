package nl.rijksoverheid.mgo.data.medication

import nl.rijksoverheid.mgo.data.api.dva.createDvaApi
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.data.uiSchema.TestUiSchemaRepository
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServerRule
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
    fun `Given successful request, When getting medications, Then return ui schema`() =
        runTest {
            // Given
            testServer.enqueue200()

            // When
            val repository = getRepository(uiSchemaList = listOf(TEST_UI_SCHEMA_MEDICATION))
            val result = repository.getMedications(organizationId = "", resourceEndpoint = "")

            // Then
            assertEquals(listOf(TEST_UI_SCHEMA_MEDICATION), result.getOrNull())
        }

    @Test
    fun `Given request errors, When getting medications, Then return error`() =
        runTest {
            // Given
            testServer.enqueue500()

            // When
            val repository = getRepository(uiSchemaList = listOf(TEST_UI_SCHEMA_MEDICATION))
            val result = repository.getMedications(organizationId = "", resourceEndpoint = "")

            // Then
            val exception = result.exceptionOrNull() as? HttpException
            assertEquals(500, exception?.code())
        }

    private fun getRepository(uiSchemaList: List<UISchema>): DefaultMedicationRepository {
        val okHttpClient = TEST_OKHTTP_CLIENT
        val dvaApi = createDvaApi(okHttpClient = okHttpClient, baseUrl = testServer.url())
        val uiSchemaMapper = TestUiSchemaMapper(uiSchemaList)
        return DefaultMedicationRepository(dvaApi = dvaApi, uiSchemaMapper = uiSchemaMapper, uiSchemaRepository = TestUiSchemaRepository())
    }
}
