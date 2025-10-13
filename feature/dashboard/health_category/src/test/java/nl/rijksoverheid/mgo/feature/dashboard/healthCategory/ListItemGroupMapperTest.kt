package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.fhir.FhirResponseJsonSource
import nl.rijksoverheid.mgo.data.hcimParser.JvmQuickJsRepository
import nl.rijksoverheid.mgo.data.hcimParser.javascript.JsEngineRepository
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ListItemGroupMapperTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val jvmQuickJsRepository = JvmQuickJsRepository()
  private val jsEngineRepository = JsEngineRepository(jvmQuickJsRepository)
  private val mgoResourceParser = MgoResourceParser(jsEngineRepository)
  private val uiSchemaParser = UiSchemaParser(jsEngineRepository)
  private val getDataSetsFromDisk = JvmGetDataSetsFromDisk()
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val organizationRepository = TestOrganizationRepository()
  private val mapper =
    ListItemGroupMapper(
      context = context,
      mgoResourceParser = mgoResourceParser,
      uiSchemaParser = uiSchemaParser,
      getDataSetsFromDisk = getDataSetsFromDisk,
      organizationRepository = organizationRepository,
    )

  @Before
  fun setup() =
    runTest {
      jvmQuickJsRepository.create()
    }

  @Test
  fun testInvoke() =
    runTest {
      // Given: A organization is stored
      organizationRepository.setStoredProviders(listOf(TEST_MGO_ORGANIZATION))

      // Given: The lifestyle category
      val category = getHealthCategoriesFromDisk().map { group -> group.categories }.flatten().first { category -> category.id == "lifestyle" }

      // Given: Five fhir responses that are part of the lifestyle category
      val fhirResponses =
        listOf(
          FhirResponse.Success(
            organizationId = "1",
            dataServiceId = "48",
            endpointId = "1",
            jsonSource = FhirResponseJsonSource.Memory(json = getFhirResourceJson("livingSituation.json")),
            isEmpty = false,
          ),
          FhirResponse.Success(
            organizationId = "1",
            dataServiceId = "48",
            endpointId = "1",
            jsonSource = FhirResponseJsonSource.Memory(json = getFhirResourceJson("alcoholUse.json")),
            isEmpty = false,
          ),
          FhirResponse.Success(
            organizationId = "1",
            dataServiceId = "48",
            endpointId = "1",
            jsonSource = FhirResponseJsonSource.Memory(json = getFhirResourceJson("drugUse.json")),
            isEmpty = false,
          ),
          FhirResponse.Success(
            organizationId = "1",
            dataServiceId = "48",
            endpointId = "1",
            jsonSource = FhirResponseJsonSource.Memory(json = getFhirResourceJson("tobaccoUse.json")),
            isEmpty = false,
          ),
          FhirResponse.Success(
            organizationId = "1",
            dataServiceId = "48",
            endpointId = "1",
            jsonSource = FhirResponseJsonSource.Memory(json = getFhirResourceJson("nutritionAdvice.json")),
            isEmpty = false,
          ),
        )

      // When: Mapping to groups
      val groups =
        mapper.invoke(
          category = category,
          fhirResponses = fhirResponses,
        )

      // Then: Groups are returned with mgo resources
      assertEquals(5, groups.size)
      assertEquals(1, groups[0].items.size)
      assertEquals(1, groups[1].items.size)
      assertEquals(1, groups[2].items.size)
      assertEquals(1, groups[3].items.size)
      assertEquals(1, groups[4].items.size)
    }

  private fun getFhirResourceJson(fileName: String) =
    this::class.java.classLoader
      ?.getResource(fileName)!!
      .readText(Charsets.UTF_8)
}
