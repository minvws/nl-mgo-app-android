package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.api.dva.createDvaApi
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataService
import nl.rijksoverheid.mgo.data.localisation.models.TEST_BGZ_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_GP_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServerRule
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class DefaultHealthCareRepositoryTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer

    @Test
    fun `Given successful requests, When calling getUiSchema with Medication category, Then return ui schemas`() =
        runTest {
            // Given
            val uiSchemaMapper = TestUiSchemaMapper(listOf(TEST_UI_SCHEMA_MEDICATION))
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            testServer.enqueue200(amount = 4)
            val repository = DefaultHealthCareRepository(uiSchemaMapper = uiSchemaMapper, dvaApi = dvaApi, dvaApiBaseUrl = "")

            // When
            val result =
                repository.getUiSchema(
                    organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_BGZ_DATA_SERVICE, TEST_GP_DATA_SERVICE)),
                    category = HealthCareCategory.MEDICATIONS,
                )

            // Then
            assertEquals(4, result.size)
        }
}
