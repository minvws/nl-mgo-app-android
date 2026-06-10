package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.fhir.ObserveFhirResponses
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_SUCCESS
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.type.HealthCategoryScreenType
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ObserveListItemsStateTest {
  private val observeFhirResponses = mockk<ObserveFhirResponses>(relaxed = true)
  private val mgoResourceParser = mockk<MgoResourceParser>(relaxed = true)
  private val mgoResourceStore = MgoResourceStore()
  private val listItemsStateMapper = mockk<ListItemsStateMapper>(relaxed = true)
  private val usecase =
    ObserveListItemsState(
      observeFhirResponses = observeFhirResponses,
      mgoResourceParser = mgoResourceParser,
      mgoResourceStore = mgoResourceStore,
      listItemsStateMapper = listItemsStateMapper,
      mgoByteArrayStorage = MemoryMgoByteArrayStorage(),
    )

  @Test
  fun testInvoke() =
    runTest {
      // Given: Fhir responses
      every { observeFhirResponses.invoke(any(), any()) } answers { flowOf(listOf(TEST_FHIR_RESPONSE_SUCCESS())) }

      // Given: Mgo resources can be created
      coEvery { mgoResourceParser.invoke(any(), any(), any(), any()) } answers { listOf(TEST_MGO_RESOURCE) }

      // When: Calling use case
      usecase.invoke(type = HealthCategoryScreenType.SUBCATEGORY, category = TEST_HEALTH_CATEGORY_PROBLEMS, organizations = listOf()).test {
        awaitItem()

        // Then: Mgo resource is cached
        assertEquals(TEST_MGO_RESOURCE, mgoResourceStore.get("1"))

        // Then: List item state mapper is called
        coVerify(exactly = 1) { listItemsStateMapper.invoke(any(), any(), any(), any()) }

        awaitComplete()
      }
    }
}
