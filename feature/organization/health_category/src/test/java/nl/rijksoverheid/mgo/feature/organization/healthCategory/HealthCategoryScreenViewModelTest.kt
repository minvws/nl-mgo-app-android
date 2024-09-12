package nl.rijksoverheid.mgo.feature.organization.healthCategory

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.TEST_HEALTH_CARE_DATA_LOADED_MEDICATION
import nl.rijksoverheid.mgo.data.healthcare.TestHealthCareRepository
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
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
            val healthCareRepository = TestHealthCareRepository()
            healthCareRepository.addHealthCareData(
                category = HealthCareCategory.MEDICATIONS,
                data = listOf(TEST_HEALTH_CARE_DATA_LOADED_MEDICATION),
            )

            // When
            val viewModel = HealthCategoryScreenViewModel(healthCareRepository = healthCareRepository)

            // Then
            viewModel.viewState.test {
                assertEquals(listOf(TEST_LIST_ITEM_1), awaitItem().listItems)
            }
        }
}
