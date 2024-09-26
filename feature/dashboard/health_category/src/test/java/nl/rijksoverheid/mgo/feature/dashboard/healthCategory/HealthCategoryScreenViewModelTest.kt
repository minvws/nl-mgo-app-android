package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.TEST_HEALTH_CARE_DATA_STATE_ERROR
import nl.rijksoverheid.mgo.data.healthcare.TEST_HEALTH_CARE_DATA_STATE_LOADED
import nl.rijksoverheid.mgo.data.healthcare.TestHealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class HealthCategoryScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given medications, When creating viewmodel, List items are shown`() =
        runTest {
            // Given
            val healthCareDataStatesRepository = TestHealthCareDataStatesRepository(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED))
            val organizationRepository = TestOrganizationRepository()

            // When
            val viewModel =
                HealthCategoryScreenViewModel(
                    arguments = HealthCategoryScreenArguments(category = HealthCareCategory.MEDICATIONS, filterOrganization = null),
                    healthCareDataStatesRepository = healthCareDataStatesRepository,
                    organizationRepository = organizationRepository,
                )

            // Then
            viewModel.viewState.test {
                val emit = awaitItem()
                assertTrue(emit.listItemsState is HealthCategoryScreenViewState.ListItemsState.Loaded)
            }
        }

    @Test
    fun `Given failed medications for organization, When calling retry, Show error banner is updated`() =
        runTest {
            // Given
            val healthCareDataStatesRepository = TestHealthCareDataStatesRepository(listOf(TEST_HEALTH_CARE_DATA_STATE_ERROR))
            healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED))
            val organizationRepository = TestOrganizationRepository()

            // When
            val viewModel =
                HealthCategoryScreenViewModel(
                    arguments =
                        HealthCategoryScreenArguments(
                            category = HealthCareCategory.MEDICATIONS,
                            filterOrganization = TEST_MGO_ORGANIZATION,
                        ),
                    healthCareDataStatesRepository = healthCareDataStatesRepository,
                    organizationRepository = organizationRepository,
                )

            // When
            viewModel.viewState.test {
                val emit1 = awaitItem()
                assertTrue(emit1.showErrorBanner)

                viewModel.retry()

                // Then
                val emit2 = awaitItem()
                assertFalse(emit2.showErrorBanner)
            }
        }

    @Test
    fun `Given failed medications for category, When calling retry, Show error banner is updated`() =
        runTest {
            // Given
            val healthCareDataStatesRepository = TestHealthCareDataStatesRepository(listOf(TEST_HEALTH_CARE_DATA_STATE_ERROR))
            healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED))
            val organizationRepository = TestOrganizationRepository()
            organizationRepository.setStoredProviders(listOf(TEST_MGO_ORGANIZATION))

            // When
            val viewModel =
                HealthCategoryScreenViewModel(
                    arguments = HealthCategoryScreenArguments(category = HealthCareCategory.MEDICATIONS, filterOrganization = null),
                    healthCareDataStatesRepository = healthCareDataStatesRepository,
                    organizationRepository = organizationRepository,
                )

            // When
            viewModel.viewState.test {
                val emit1 = awaitItem()
                assertTrue(emit1.showErrorBanner)

                viewModel.retry()

                // Then
                val emit2 = awaitItem()
                assertFalse(emit2.showErrorBanner)
            }
        }
}
