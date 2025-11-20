package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.healthCategories.FavoriteHealthCategoriesRepository
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val keyValueStorage = MemoryMgoKeyValueStorage()
  private val favoriteRepository = FavoriteHealthCategoriesRepository(keyValueStorage)

  private val okHttpClient = OkHttpClient()
  private val organizationRepository = OrganizationRepository(okHttpClient = okHttpClient, baseUrl = "", mgoByteArrayStorage = MemoryMgoByteArrayStorage())
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val keyValueStore = TestKeyValueStore()

  @Before
  fun setup() =
    runTest {
      organizationRepository.save(TEST_MGO_ORGANIZATION)
    }

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
