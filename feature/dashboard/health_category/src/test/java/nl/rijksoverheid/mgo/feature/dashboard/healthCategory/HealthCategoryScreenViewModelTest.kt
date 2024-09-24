package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.TEST_HEALTH_CARE_DATA_STATE_LOADED
import nl.rijksoverheid.mgo.data.healthcare.TestHealthCareStateRepository
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
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
            val healthCareStateRepository = TestHealthCareStateRepository()
            healthCareStateRepository.setData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED))

            // When
            val viewModel =
                HealthCategoryScreenViewModel(
                    arguments = HealthCategoryScreenArguments(category = HealthCareCategory.MEDICATIONS, filterOrganization = null),
                    healthCareStateRepository = healthCareStateRepository,
                )

            // Then
            viewModel.viewState.test {
                val emit = awaitItem()
                assertTrue(emit.listItemsState is HealthCategoryScreenViewState.ListItemsState.Loaded)
            }
        }
}
