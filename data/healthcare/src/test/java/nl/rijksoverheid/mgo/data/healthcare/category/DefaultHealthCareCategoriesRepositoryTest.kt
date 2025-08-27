package nl.rijksoverheid.mgo.data.healthcare.category

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.DefaultHealthCareCategoriesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultHealthCareCategoriesRepositoryTest {
  private val testKeyValueStore = TestKeyValueStore()
  private val repository = DefaultHealthCareCategoriesRepository(testKeyValueStore)

  @Test
  fun testObserveAndFavorite() =
    runTest {
      // Given: No favorites

      // When: Observing categories
      repository.observe().test {
        // Then: All categories are emitted with no favorites
        val expected1: List<HealthCareCategory> = HealthCareCategoryId.entries.map { id -> HealthCareCategory(id = id, favorite = false) }
        assertEquals(expected1, awaitItem())

        // When: Marking a category as favorite
        repository.favorite(HealthCareCategoryId.MEDICATIONS, true)

        // Then: All categories are emitted with medication marked as favorite
        val expected2: List<HealthCareCategory> =
          HealthCareCategoryId.entries.map { id ->
            HealthCareCategory(
              id = id,
              favorite =
                id == HealthCareCategoryId.MEDICATIONS,
            )
          }

        assertEquals(expected2, awaitItem())
      }
    }
}
