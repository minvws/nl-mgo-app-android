package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.type

import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS
import org.junit.Assert.assertEquals
import org.junit.Test

internal class GetHealthCategoryScreenTypeTest {
  private val usecase = GetHealthCategoryScreenType()

  @Test
  fun testCategoryDocuments() {
    // Given: category is documents
    val category = TEST_HEALTH_CATEGORY_PROBLEMS.copy(id = "documents")

    // When: Calling invoke
    val type = usecase.invoke(category = category)

    // Then: Type is date
    assertEquals(type, HealthCategoryScreenType.DATE)
  }

  @Test
  fun testCategoryProblems() {
    // Given: category is documents
    val category = TEST_HEALTH_CATEGORY_PROBLEMS

    // When: Calling invoke
    val type = usecase.invoke(category = category)

    // Then: Type is date
    assertEquals(type, HealthCategoryScreenType.SUBCATEGORY)
  }
}
