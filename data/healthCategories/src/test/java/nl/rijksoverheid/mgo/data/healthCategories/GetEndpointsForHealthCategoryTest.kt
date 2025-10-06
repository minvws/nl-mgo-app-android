package nl.rijksoverheid.mgo.data.healthCategories

import org.junit.Assert.assertEquals
import org.junit.Test

class GetEndpointsForHealthCategoryTest {
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val usecase =
    GetEndpointsForHealthCategory(
      getDataSetsFromDisk = JvmGetDataSetsFromDisk(),
    )

  @Test
  fun testGetEndpoints() {
    // Given: The lifestyle health category
    val groups = getHealthCategoriesFromDisk()
    val categories = groups.map { group -> group.categories }.flatten()
    val category = categories.first { category -> category.id == "lifestyle" }

    // When: Calling get endpoints
    val endpointsWithDataSetId = usecase.invoke(category)

    // Then: Correct endpoints for that category are returned
    assertEquals(1, endpointsWithDataSetId.size)
    assertEquals(5, endpointsWithDataSetId[0].endpoints.size)
    assertEquals("livingSituation", endpointsWithDataSetId[0].endpoints[0].id)
  }
}
