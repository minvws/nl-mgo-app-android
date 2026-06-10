package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_SERVER_ERROR
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_SUCCESS
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_USER_ERROR
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_MEDICATION
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup.ListItemsGroupedByDateMapper
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.listItemGroup.ListItemsGroupedBySubcategoryMapper
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.type.HealthCategoryScreenType
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ListItemsStateMapperTest {
  private val groupedBySubcategoryMapper = mockk<ListItemsGroupedBySubcategoryMapper>(relaxed = true)
  private val groupedByDateMapper = mockk<ListItemsGroupedByDateMapper>(relaxed = true)
  private val mapper = ListItemsStateMapper(groupedBySubcategoryMapper = groupedBySubcategoryMapper, groupedByDateMapper = groupedByDateMapper)

  @Test
  fun testHasUserError() =
    runTest {
      // Given: user error
      val responses = listOf(TEST_FHIR_RESPONSE_USER_ERROR)

      // When: Calling invoke
      val listItemsState =
        mapper.invoke(
          responses = responses,
          mgoResources = listOf(),
          category = TEST_HEALTH_CATEGORY_MEDICATION,
          type = HealthCategoryScreenType.SUBCATEGORY,
        )

      // Then: List items state is returned
      assertEquals(HealthCategoryScreenViewState.ListItemsState.Error.UserError, listItemsState)
    }

  @Test
  fun testHasServerError() =
    runTest {
      // Given: user error
      val responses =
        listOf(TEST_FHIR_RESPONSE_SERVER_ERROR)

      // When: Calling invoke
      val listItemsState =
        mapper.invoke(
          responses = responses,
          mgoResources = listOf(),
          category = TEST_HEALTH_CATEGORY_MEDICATION,
          type = HealthCategoryScreenType.SUBCATEGORY,
        )

      // Then: List items state is returned
      assertEquals(HealthCategoryScreenViewState.ListItemsState.Error.ServerError, listItemsState)
    }

  @Test
  fun testHasMultipleErrors() =
    runTest {
      // Given: user error
      val responses =
        listOf(TEST_FHIR_RESPONSE_SERVER_ERROR, TEST_FHIR_RESPONSE_USER_ERROR)

      // When: Calling invoke
      val listItemsState =
        mapper.invoke(
          responses = responses,
          mgoResources = listOf(),
          category = TEST_HEALTH_CATEGORY_MEDICATION,
          type = HealthCategoryScreenType.SUBCATEGORY,
        )

      // Then: List items state is returned
      assertEquals(HealthCategoryScreenViewState.ListItemsState.Error.UserError, listItemsState)
    }

  @Test
  fun testEmpty() =
    runTest {
      // Given: user error
      val responses =
        listOf(TEST_FHIR_RESPONSE_SUCCESS(isEmpty = true))

      // When: Calling invoke
      val listItemsState =
        mapper.invoke(
          responses = responses,
          mgoResources = listOf(),
          category = TEST_HEALTH_CATEGORY_MEDICATION,
          type = HealthCategoryScreenType.SUBCATEGORY,
        )

      // Then: List items state is returned
      assertEquals(HealthCategoryScreenViewState.ListItemsState.NoData, listItemsState)
    }

  @Test
  fun testLoadedTypeDate() =
    runTest {
      // Given: user error
      val responses =
        listOf(TEST_FHIR_RESPONSE_SUCCESS(isEmpty = false))

      // When: Calling invoke
      val listItemsState =
        mapper.invoke(
          responses = responses,
          mgoResources = listOf(),
          category = TEST_HEALTH_CATEGORY_MEDICATION,
          type = HealthCategoryScreenType.DATE,
        )

      // Then: Date mapper is called
      coVerify(exactly = 1) { groupedByDateMapper.invoke(any()) }

      // Then: List items state is returned
      assertEquals(HealthCategoryScreenViewState.ListItemsState.Loaded(listOf()), listItemsState)
    }

  @Test
  fun testLoadedTypeSubcategory() =
    runTest {
      // Given: user error
      val responses =
        listOf(TEST_FHIR_RESPONSE_SUCCESS(isEmpty = false))

      // When: Calling invoke
      val listItemsState =
        mapper.invoke(
          responses = responses,
          mgoResources = listOf(),
          category = TEST_HEALTH_CATEGORY_MEDICATION,
          type = HealthCategoryScreenType.SUBCATEGORY,
        )

      // Then: Date mapper is called
      coVerify(exactly = 1) { groupedBySubcategoryMapper.invoke(any(), any()) }

      // Then: List items state is returned
      assertEquals(HealthCategoryScreenViewState.ListItemsState.Loaded(listOf()), listItemsState)
    }
}
