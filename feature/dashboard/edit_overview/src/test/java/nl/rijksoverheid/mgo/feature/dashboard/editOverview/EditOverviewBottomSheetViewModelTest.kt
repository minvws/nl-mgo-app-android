package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthCategories.FavoriteHealthCategoriesRepository
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class EditOverviewBottomSheetViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val keyValueStorage = MemoryMgoKeyValueStorage()
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val favoriteRepository = FavoriteHealthCategoriesRepository(keyValueStorage)
  private val groups = getHealthCategoriesFromDisk()

  @Test
  fun testInitialViewState() =
    runTest {
      // Given: First category is marked as favorite
      val firstCategory = groups.first().categories.first()
      favoriteRepository.store(listOf(firstCategory.id))

      // When: Creating viewmodel
      val viewModel = createViewModel()

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(1, viewState.favorites.size)
        assertEquals(4, viewState.nonFavorites.size)
      }
    }

  @Test
  fun testSave() =
    runTest {
      // Given: Nothing marked as favorite
      favoriteRepository.store(listOf())

      // Given: viewmodel
      val viewModel = createViewModel()

      // When: Calling save
      val firstCategory = groups.first().categories.first()
      viewModel.save(favorites = listOf(firstCategory), nonFavorites = groups)

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(1, viewState.favorites.size)
        assertEquals(4, viewState.nonFavorites.size)
      }
    }

  private fun createViewModel(): EditOverviewBottomSheetViewModel =
    EditOverviewBottomSheetViewModel(
      ioDispatcher = mainDispatcherRule.testDispatcher,
      getHealthCategoriesFromDisk = getHealthCategoriesFromDisk,
      favoriteRepository = favoriteRepository,
    )
}
