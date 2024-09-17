package nl.rijksoverheid.mgo.data.healthcare

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.api.dva.createDvaApi
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServerRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest

internal class DefaultHealthCareRepositoryTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer

    @Test
    fun `Given medication request is success, When calling getMedications, Then update state`() =
        runTest {
            // Given
            val uiSchemaMapper = TestUiSchemaMapper(listOf(TEST_UI_SCHEMA_MEDICATION))
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            testServer.enqueue200()
            val repository =
                DefaultHealthCareRepository(
                    uiSchemaMapper = uiSchemaMapper,
                    dvaApi = dvaApi,
                )

            repository.observeData(HealthCareCategory.MEDICATIONS).test {
                // When
                repository.getMedications(organization = TEST_MGO_ORGANIZATION)

                // Then
                assertTrue(awaitItem().first() is HealthCareData.Loading)
                assertTrue(awaitItem().first() is HealthCareData.Loaded)
            }
        }

    @Test
    fun `Given medication request is success, When calling getMedications and observing single organization, Then update state`() =
        runTest {
            // Given
            val uiSchemaMapper = TestUiSchemaMapper(listOf(TEST_UI_SCHEMA_MEDICATION))
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            testServer.enqueue200()
            val repository =
                DefaultHealthCareRepository(
                    uiSchemaMapper = uiSchemaMapper,
                    dvaApi = dvaApi,
                )

            repository.observeData(category = HealthCareCategory.MEDICATIONS, filterOrganization = TEST_MGO_ORGANIZATION).test {
                // When
                repository.getMedications(organization = TEST_MGO_ORGANIZATION)

                // Then
                assertTrue(awaitItem().first() is HealthCareData.Loading)
                assertTrue(awaitItem().first() is HealthCareData.Loaded)
            }
        }

    @Test
    fun `Given medication request is success, When calling getMedications and observing single organization, Then don't update state`() =
        runTest {
            // Given
            val uiSchemaMapper = TestUiSchemaMapper(listOf(TEST_UI_SCHEMA_MEDICATION))
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            testServer.enqueue200()
            val repository =
                DefaultHealthCareRepository(
                    uiSchemaMapper = uiSchemaMapper,
                    dvaApi = dvaApi,
                )

            repository.observeData(category = HealthCareCategory.MEDICATIONS, filterOrganization = TEST_MGO_ORGANIZATION).test {
                // When
                repository.getMedications(organization = TEST_MGO_ORGANIZATION.copy(id = "2"))
            }
        }

    @Test
    fun `Given medication request is success and medication already loaded, When calling getMedications, No state is updated`() =
        runTest {
            // Given
            val uiSchemaMapper = TestUiSchemaMapper(listOf(TEST_UI_SCHEMA_MEDICATION))
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            testServer.enqueue200()
            val repository =
                DefaultHealthCareRepository(
                    uiSchemaMapper = uiSchemaMapper,
                    dvaApi = dvaApi,
                )

            // Start with medication already added
            repository.medications.update {
                mapOf(
                    TEST_MGO_ORGANIZATION to
                        HealthCareData.Loaded(
                            organization = TEST_MGO_ORGANIZATION,
                            uiSchemaList = listOf(TEST_UI_SCHEMA_MEDICATION),
                        ),
                )
            }

            repository.observeData(HealthCareCategory.MEDICATIONS).test {
                // When
                repository.getMedications(organization = TEST_MGO_ORGANIZATION)

                // Then
                assertTrue(awaitItem().first() is HealthCareData.Loaded)
            }
        }

    @Test
    fun `Given medication request failed, When calling getMedications, Then update state`() =
        runTest {
            // Given
            val uiSchemaMapper = TestUiSchemaMapper(listOf(TEST_UI_SCHEMA_MEDICATION))
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            testServer.enqueue500()
            val repository =
                DefaultHealthCareRepository(
                    uiSchemaMapper = uiSchemaMapper,
                    dvaApi = dvaApi,
                )

            repository.observeData(HealthCareCategory.MEDICATIONS).test {
                // When
                repository.getMedications(organization = TEST_MGO_ORGANIZATION)

                // Then
                assertTrue(awaitItem().first() is HealthCareData.Loading)
                assertTrue(awaitItem().first() is HealthCareData.Error)
            }
        }

    @Test
    fun `Given only medications implemented, When calling observeData with other category, Then do not emit anything`() =
        runTest {
            val uiSchemaMapper = TestUiSchemaMapper(listOf(TEST_UI_SCHEMA_MEDICATION))
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            val repository = DefaultHealthCareRepository(uiSchemaMapper = uiSchemaMapper, dvaApi = dvaApi)
            repository.observeData(HealthCareCategory.ALLERGIES).test {
                awaitComplete()
            }
        }
}
