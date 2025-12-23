package nl.rijksoverheid.mgo.component.fhir

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_ALLERGIES
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class GetEndpointsTest {
  private val getDataSetFromDisk = JvmGetDataSetsFromDisk()
  private val getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetFromDisk)
  private val getEndpoints = GetEndpoints(getEndpointsForHealthCategory)

  @Test
  fun testInvoke() =
    runTest {
      // Given: Two organizations
      val organizations =
        listOf(
          TEST_MGO_ORGANIZATION.copy(id = "1"),
          TEST_MGO_ORGANIZATION.copy(id = "2"),
        )

      // Given: Two categories
      val categories =
        listOf(
          TEST_HEALTH_CATEGORY_PROBLEMS,
          TEST_HEALTH_CATEGORY_ALLERGIES,
        )

      // When: Calling invoke
      val endpoints = getEndpoints(organizations = organizations, categories = categories)

      // Then: Endpoints are returned
      assertEquals(2, endpoints.size)
    }
}
