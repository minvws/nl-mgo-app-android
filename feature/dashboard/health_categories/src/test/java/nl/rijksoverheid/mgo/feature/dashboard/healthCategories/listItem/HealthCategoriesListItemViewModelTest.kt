package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.HealthCareData
import nl.rijksoverheid.mgo.data.healthcare.TestHealthCareRepository
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

    private val healthCareRepository = TestHealthCareRepository()

    @Test
    fun `Given health care data emits one loading, When creating viewmodel, Then list item state is updated`() =
        runTest {
            // Given
            healthCareRepository.addHealthCareData(category = HealthCareCategory.MEDICATIONS, data = listOf(HealthCareData.Loading))

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareRepository = healthCareRepository,
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
            healthCareRepository.addHealthCareData(
                category = HealthCareCategory.MEDICATIONS,
                data =
                    listOf(
                        HealthCareData.Loading,
                        HealthCareData.Loaded(organization = TEST_MGO_ORGANIZATION, uiSchemaList = listOf(TEST_UI_SCHEMA_MEDICATION)),
                        HealthCareData.Error(IllegalStateException("Something went wrong")),
                    ),
            )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareRepository = healthCareRepository,
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
            healthCareRepository.addHealthCareData(
                category = HealthCareCategory.MEDICATIONS,
                data =
                    listOf(
                        HealthCareData.Loaded(
                            organization = TEST_MGO_ORGANIZATION,
                            uiSchemaList = listOf(TEST_UI_SCHEMA_MEDICATION),
                        ),
                    ),
            )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareRepository = healthCareRepository,
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
            healthCareRepository.addHealthCareData(
                category = HealthCareCategory.MEDICATIONS,
                data =
                    listOf(
                        HealthCareData.Loaded(
                            organization = TEST_MGO_ORGANIZATION,
                            uiSchemaList = listOf(),
                        ),
                    ),
            )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareRepository = healthCareRepository,
                )

            // Then
            viewModel.listItemState.test {
                assertTrue(awaitItem() == HealthCategoriesListItemState.NO_DATA)
            }
        }

    @Test
    fun `Given health care data emits two errors, When creating viewmodel, Then list item state is updated`() =
        runTest {
            // Given
            healthCareRepository.addHealthCareData(
                category = HealthCareCategory.MEDICATIONS,
                data =
                    listOf(
                        HealthCareData.Error(IllegalStateException("Something went wrong")),
                        HealthCareData.Error(IllegalStateException("Something went wrong")),
                    ),
            )

            // When
            val viewModel =
                HealthCategoriesListItemViewModel(
                    filterOrganization = null,
                    category = HealthCareCategory.MEDICATIONS,
                    healthCareRepository = healthCareRepository,
                )

            // Then
            viewModel.listItemState.test {
                assertTrue(awaitItem() == HealthCategoriesListItemState.NO_DATA)
            }
        }
}
