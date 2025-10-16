package nl.rijksoverheid.mgo.data.healthCategories

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FavoriteHealthCategoriesRepositoryTest {
  private val keyValueStorage = MemoryMgoKeyValueStorage()
  private val repository = FavoriteHealthCategoriesRepository(keyValueStorage)

  @Before
  fun setup() {
    keyValueStorage.deleteAll()
  }

  @Test
  fun testGetFavorites() =
    runTest {
      // Given: Favorites are stored
      repository.store(listOf("1", "2", "3"))

      // When: Observing favorites
      repository.observe().test {
        // Then: Favorites are emitted
        assertEquals(listOf("1", "2", "3"), awaitItem())
      }
    }

  @Test
  fun testGetFavoritesEmpty() =
    runTest {
      // Given: No favorites are stored
      repository.store(listOf())

      // When: Observing favorites
      repository.observe().test {
        // Then: Favorites are emitted
        assertEquals(0, awaitItem().size)
      }
    }
}
