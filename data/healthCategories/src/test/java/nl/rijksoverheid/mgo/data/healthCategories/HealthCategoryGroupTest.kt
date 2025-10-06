package nl.rijksoverheid.mgo.data.healthCategories

import nl.rijksoverheid.mgo.data.healthCategories.models.getEndpoints
import org.junit.Assert.assertEquals
import org.junit.Test

class HealthCategoryGroupTest {
  private val healthCategoriesRepository = JvmHealthCategoriesRepository()

  @Test
  fun testGetEndpoints() {
    // Given: Groups and data sets
    val groups = healthCategoriesRepository.getGroups()
    val dataSets = healthCategoriesRepository.getDataSets()

    // Given: The lifestyle health category
    val categories = groups.map { group -> group.categories }.flatten()
    val category = categories.first { category -> category.id == "lifestyle" }

    // When: Calling get endpoints
    val endpointsWithDataSetId = category.getEndpoints(dataSets)

    // Then: Correct endpoints for that category are returned
    assertEquals(1, endpointsWithDataSetId.size)
    assertEquals(5, endpointsWithDataSetId[0].endpoints.size)
    assertEquals("livingSituation", endpointsWithDataSetId[0].endpoints[0].id)
  }
}
