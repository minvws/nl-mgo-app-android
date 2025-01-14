package nl.rijksoverheid.mgo.data.healthcare

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.fhirParser.shared.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.DefaultHealthCareDataStateRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class DefaultHealthCareDataStateRepositoryTest {
    @Test
    fun testEmptyState() =
        runTest {
            // Given: UI Schema returns no result
            val uiSchemaRepository = TestUiSchemaRepository()
            uiSchemaRepository.setUiSchemaResult(listOf())
            val repository = DefaultHealthCareDataStateRepository(uiSchemaRepository)

            // When: Calling get
            repository.get(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS).test {
                // Then: First emit loading state, and then empty state
                assertTrue(awaitItem() is HealthCareDataState.Loading)
                assertTrue(awaitItem() is HealthCareDataState.Empty)
                awaitComplete()
            }
        }

    @Test
    fun testLoadedState() =
        runTest {
            // Given: UI Schema returns result
            val uiSchemaRepository = TestUiSchemaRepository()
            uiSchemaRepository.setUiSchemaResult(
                listOf(
                    Result.success(
                        listOf(
                            TEST_UI_SCHEMA_MEDICATION,
                        ),
                    ),
                ),
            )
            val repository = DefaultHealthCareDataStateRepository(uiSchemaRepository)

            // When: Calling get
            repository.get(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS).test {
                // Then: First emit loading state, and then loaded state
                assertTrue(awaitItem() is HealthCareDataState.Loading)
                assertTrue(awaitItem() is HealthCareDataState.Loaded)
                awaitComplete()
            }
        }
}
