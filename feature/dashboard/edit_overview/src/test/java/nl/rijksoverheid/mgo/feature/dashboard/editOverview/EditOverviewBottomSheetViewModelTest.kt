package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import app.cash.turbine.test
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthcare.category.TestHealthCareCategoriesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class EditOverviewBottomSheetViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val healthCareCategoryRepository = TestHealthCareCategoriesRepository()

  @Test
  fun testReorderFavorites() =
    runTest {
      // Given: Medication and appointments are marked as favorite
      healthCareCategoryRepository.setFavorites(listOf(HealthCareCategoryId.MEDICATIONS, HealthCareCategoryId.APPOINTMENTS))

      // When: Switching medication with appointments
      val viewModel = getViewModel()
      viewModel.reorderFavorites(0, 1)

      viewModel.viewState.map { viewState -> viewState.favorites }.test {
        // Then: Appointments in now the first favorite, and medication the second
        assertEquals(listOf(HealthCareCategoryId.APPOINTMENTS, HealthCareCategoryId.MEDICATIONS), awaitItem())
      }
    }

  @Test
  fun testSave() =
    runTest {
      // Given: Nothing is marked as favorite
      healthCareCategoryRepository.setFavorites(listOf())

      // When: Clicking save
      val viewModel = getViewModel()
      viewModel.save(favorites = listOf(HealthCareCategoryId.MEDICATIONS, HealthCareCategoryId.APPOINTMENTS), nonFavorites = listOf())

      healthCareCategoryRepository.observe().test {
        // Then: Favorites are saved
        val expected =
          HealthCareCategoryId.entries.map { id ->
            HealthCareCategory(
              id = id,
              favoritePosition =
                when (id) {
                  HealthCareCategoryId.MEDICATIONS -> 0
                  HealthCareCategoryId.APPOINTMENTS -> 1
                  else -> -1
                },
            )
          }
        assertEquals(expected, awaitItem())
      }
    }

  private fun getViewModel(): EditOverviewBottomSheetViewModel =
    EditOverviewBottomSheetViewModel(
      ioDispatcher = mainDispatcherRule.testDispatcher,
      healthCareCategoryRepository = healthCareCategoryRepository,
    )
}
