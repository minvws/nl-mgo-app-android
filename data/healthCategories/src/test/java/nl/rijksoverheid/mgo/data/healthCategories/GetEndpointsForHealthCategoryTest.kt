package nl.rijksoverheid.mgo.data.healthCategories

import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
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
    val endpoints = usecase.invoke(category = category, organization = TEST_MGO_ORGANIZATION)

    // Then: Correct endpoints for that category are returned
    assertEquals(5, endpoints.size)
    assertEquals("livingSituation", endpoints[0].endpointId)
  }
}
