package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import app.cash.turbine.test
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthcare.category.TestHealthCareCategoriesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class EditOverviewBottomSheetViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val healthCareCategoryRepository = TestHealthCareCategoriesRepository()

  private val viewModel =
    EditOverviewBottomSheetViewModel(
      ioDispatcher = mainDispatcherRule.testDispatcher,
      healthCareCategoryRepository = healthCareCategoryRepository,
    )

  @Test
  fun testFavorite() =
    runTest {
      // Given: No favorites

      // When: Marking the medications category as favorite
      viewModel.clickFavorite(HealthCareCategoryId.MEDICATIONS, true)

      viewModel.viewState.map { viewState -> viewState.favorites }.test {
        // Then: Medication category is favorite
        assertEquals(listOf(HealthCareCategoryId.MEDICATIONS), awaitItem())
      }
    }

  @Test
  fun testUnFavorite() =
    runTest {
      // Given: Medication is marked as favorite
      viewModel.clickFavorite(HealthCareCategoryId.MEDICATIONS, true)

      // When: Marking the medications category no longer as favorite
      viewModel.clickFavorite(HealthCareCategoryId.MEDICATIONS, false)

      viewModel.viewState.map { viewState -> viewState.favorites }.test {
        // Then: No favorites
        assertEquals(listOf<HealthCareCategoryId>(), awaitItem())
      }
    }

  @Test
  fun testReorderFavorites() =
    runTest {
      // Given: Medication and appointments are marked as favorite
      viewModel.clickFavorite(HealthCareCategoryId.MEDICATIONS, true)
      viewModel.clickFavorite(HealthCareCategoryId.APPOINTMENTS, true)

      // When: Switching medication with appointments
      viewModel.reorderFavorites(0, 1)

      viewModel.viewState.map { viewState -> viewState.favorites }.test {
        // Then: Appointments in now the first favorite, and medication the second
        assertEquals(listOf(HealthCareCategoryId.APPOINTMENTS, HealthCareCategoryId.MEDICATIONS), awaitItem())
      }
    }

  @Test
  fun testSave() =
    runTest {
      // Given: Medication and appointments are marked as favorite
      viewModel.clickFavorite(HealthCareCategoryId.MEDICATIONS, true)
      viewModel.clickFavorite(HealthCareCategoryId.APPOINTMENTS, true)

      // When: Clicking save
      viewModel.save()

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

  @Test
  fun testOnClear() =
    runTest {
      // When: Calling onClear
      viewModel.onClear()

      // Then: state is reset
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(0, viewState.favorites.size)
      }
    }
}
