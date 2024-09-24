package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.HealthCareData
import nl.rijksoverheid.mgo.data.healthcare.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.TestHealthCareRepository
import nl.rijksoverheid.mgo.data.healthcare.TestHealthCareStateRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class HealthCategoriesListItemViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val healthCareStateRepository = TestHealthCareStateRepository()

    @Test
    fun `Given health care data emits one loading, When creating viewmodel, Then list item state is updated`() =
        runTest {
            // Given
            healthCareStateRepository.setData(
                listOf(
                    HealthCareDataState(
                        loading = true,
                        organization = TEST_MGO_ORGANIZATION,
                        category = HealthCareCategory.MEDICATIONS,
                        uiSchemaListResults = listOf()
                    )
                )
            )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareStateRepository = healthCareStateRepository
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
            healthCareStateRepository.setData(
                listOf(
                    HealthCareDataState(
                        loading = true,
                        organization = TEST_MGO_ORGANIZATION,
                        category = HealthCareCategory.MEDICATIONS,
                        uiSchemaListResults = listOf()
                    ),
                    HealthCareDataState(
                        loading = false,
                        organization = TEST_MGO_ORGANIZATION,
                        category = HealthCareCategory.MEDICATIONS,
                        uiSchemaListResults = listOf()
                    ),
                )
            )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareStateRepository = healthCareStateRepository
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
            healthCareStateRepository.setData(
                listOf(
                    HealthCareDataState(
                        loading = false,
                        organization = TEST_MGO_ORGANIZATION,
                        category = HealthCareCategory.MEDICATIONS,
                        uiSchemaListResults = listOf(Result.success(listOf(TEST_UI_SCHEMA_MEDICATION)))
                    ),
                )
            )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareStateRepository = healthCareStateRepository
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
            healthCareStateRepository.setData(
                listOf(
                    HealthCareDataState(
                        loading = false,
                        organization = TEST_MGO_ORGANIZATION,
                        category = HealthCareCategory.MEDICATIONS,
                        uiSchemaListResults = listOf(Result.success(listOf()))
                    ),
                )
            )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareStateRepository = healthCareStateRepository
                )

            // Then
            viewModel.listItemState.test {
                assertTrue(awaitItem() == HealthCategoriesListItemState.NO_DATA)
            }
        }
}
