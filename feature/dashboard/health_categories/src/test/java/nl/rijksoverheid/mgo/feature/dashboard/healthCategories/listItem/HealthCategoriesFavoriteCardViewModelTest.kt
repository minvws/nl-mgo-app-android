package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TEST_HEALTH_CARE_DATA_STATE_LOADED
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TEST_HEALTH_CARE_DATA_STATE_LOADING
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.TestHealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesFavoriteCardViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val healthCareDataStatesRepository = TestHealthCareDataStatesRepository(listOf())

  @Test
  fun testLoading() =
    runTest {
      // Given: Health care data state is loading
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADING))
      healthCareDataStatesRepository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategoryId.MEDICATIONS)

      // When: Creating viewmodel
      val viewModel = createViewModel()

      // Then: List item state is loading
      viewModel.isLoading.test {
        assertTrue(awaitItem())
      }
    }

  @Test
  fun testNotLoading() =
    runTest {
      // Given: Health care data state is loaded
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED))
      healthCareDataStatesRepository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategoryId.MEDICATIONS)

      // When: Creating viewmodel
      val viewModel = createViewModel()

      // Then: List item state is not loading
      viewModel.isLoading.test {
        assertFalse(awaitItem())
      }
    }

  private fun createViewModel(): HealthCategoriesFavoriteCardViewModel =
    HealthCategoriesFavoriteCardViewModel(
      category = HealthCareCategoryId.MEDICATIONS,
      healthCareDataStatesRepository = healthCareDataStatesRepository,
    )
}
