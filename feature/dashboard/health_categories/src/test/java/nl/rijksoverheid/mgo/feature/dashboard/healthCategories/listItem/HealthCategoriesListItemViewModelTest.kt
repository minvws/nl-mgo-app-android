package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.TEST_HEALTH_CARE_DATA_STATE_LOADED
import nl.rijksoverheid.mgo.data.healthcare.TEST_HEALTH_CARE_DATA_STATE_LOADING
import nl.rijksoverheid.mgo.data.healthcare.TestHealthCareStateRepository
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class HealthCategoriesListItemViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given health care data emits one loading, When creating viewmodel, Then list item state is updated`() =
        runTest {
            // Given
            val healthCareStateRepository =
                TestHealthCareStateRepository(
                    initialData = listOf(TEST_HEALTH_CARE_DATA_STATE_LOADING),
                )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareStateRepository = healthCareStateRepository,
                )

            // Then
            viewModel.listItemState.test {
                assertTrue(awaitItem() == HealthCategoriesListItemState.LOADING)
            }
        }

    @Test
    fun `Given health care data emits multiple with one loading, When creating viewmodel, Then list item state is updated`() =
        runTest {
            // Given
            val healthCareStateRepository =
                TestHealthCareStateRepository(
                    initialData = listOf(TEST_HEALTH_CARE_DATA_STATE_LOADING, TEST_HEALTH_CARE_DATA_STATE_LOADED),
                )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareStateRepository = healthCareStateRepository,
                )

            // Then
            viewModel.listItemState.test {
                assertTrue(awaitItem() == HealthCategoriesListItemState.LOADING)
            }
        }

    @Test
    fun `Given health care data emits one loaded, When creating viewmodel, Then list item state is updated`() =
        runTest {
            // Given
            val healthCareStateRepository =
                TestHealthCareStateRepository(
                    initialData = listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED),
                )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareStateRepository = healthCareStateRepository,
                )

            // Then
            viewModel.listItemState.test {
                assertTrue(awaitItem() == HealthCategoriesListItemState.LOADED)
            }
        }

    @Test
    fun `Given health care data emits one loaded with no data, When creating viewmodel, Then list item state is updated`() =
        runTest {
            // Given
            val healthCareStateRepository =
                TestHealthCareStateRepository(
                    initialData =
                        listOf(
                            TEST_HEALTH_CARE_DATA_STATE_LOADED.copy(
                                uiSchemaListResults = listOf(),
                            ),
                        ),
                )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareStateRepository = healthCareStateRepository,
                )

            // Then
            viewModel.listItemState.test {
                assertTrue(awaitItem() == HealthCategoriesListItemState.NO_DATA)
            }
        }
}
