package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TEST_HEALTH_CARE_DATA_STATE_EMPTY
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TEST_HEALTH_CARE_DATA_STATE_LOADED
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TEST_HEALTH_CARE_DATA_STATE_LOADING
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.TestHealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesListItemViewModelTest {
  @get:Rule
  val mainDispatcherRule = nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule()

  private val healthCareDataStatesRepository = TestHealthCareDataStatesRepository(listOf())

  @Test
  fun testLoadingState() =
    runTest {
      // Given: Health care data state is loading
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADING))
      healthCareDataStatesRepository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS)

      // When: Creating viewmodel
      val viewModel = createViewModel()

      // Then: List item state is loading
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.LOADING)
      }
    }

  @Test
  fun testOneLoadingState() =
    runTest {
      // Given: Health care data state has loading and loaded
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADING, TEST_HEALTH_CARE_DATA_STATE_LOADED))
      healthCareDataStatesRepository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS)

      // When: Creating viewmodel
      val viewModel = createViewModel()

      // Then: List item state is loading
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.LOADING)
      }
    }

  @Test
  fun testLoadedState() =
    runTest {
      // Given: Health care data state has loading and loaded
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED))
      healthCareDataStatesRepository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS)

      // When: Creating viewmodel
      val viewModel = createViewModel()

      // Then: List item state is loading
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.LOADED)
      }
    }

  @Test
  fun testNoDataState() =
    runTest {
      // Given: Health care data state has empty
      healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_EMPTY))
      healthCareDataStatesRepository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS)

      // When: Creating viewmodel
      val viewModel = createViewModel()

      // Then: List item state is no data
      viewModel.listItemState.test {
        assertTrue(awaitItem() == HealthCategoriesListItemState.NO_DATA)
      }
    }

  private fun createViewModel(): HealthCategoriesListItemViewModel {
    return HealthCategoriesListItemViewModel(
      filterOrganization = null,
      category = HealthCareCategory.MEDICATIONS,
      healthCareDataStatesRepository = healthCareDataStatesRepository,
    )
  }
}
