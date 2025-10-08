package nl.rijksoverheid.mgo.data.healthCategories

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FavoriteHealthCategoriesRepositoryTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val repository = FavoriteHealthCategoriesRepository(context)

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
}
