package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthCategories.FavoriteHealthCategoriesRepository
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class HealthCategoriesScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val favoriteRepository = FavoriteHealthCategoriesRepository(context)
  private val organizationRepository = TestOrganizationRepository()
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val keyValueStore = TestKeyValueStore()

  @Test
  fun testCreateViewModel() =
    runTest {
      // Given: First category is marked as favorite
      val firstCategory = getHealthCategoriesFromDisk()[0].categories[0].id
      favoriteRepository.store(listOf(firstCategory))

      // Given: Viewmodel
      val viewModel = createViewModel()

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(1, viewState.favorites.size)
        assertEquals(4, viewState.groups.size)
      }
    }

  private fun createViewModel() =
    HealthCategoriesScreenViewModel(
      favoriteRepository = favoriteRepository,
      organizationRepository = organizationRepository,
      getHealthCategoriesFromDisk = getHealthCategoriesFromDisk,
      keyValueStore = keyValueStore,
      ioDispatcher = mainDispatcherRule.testDispatcher,
    )
}
