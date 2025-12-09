package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.fhir.FhirRequest
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.hcimParser.JvmQuickJsRepository
import nl.rijksoverheid.mgo.data.hcimParser.javascript.JsEngineRepository
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import okhttp3.OkHttpClient
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
  private val mgoByteArrayStorage = MemoryMgoByteArrayStorage()
  private val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = mgoByteArrayStorage)
  private val mapper =
    ListItemGroupMapper(
      context = context,
      mgoResourceParser = mgoResourceParser,
      uiSchemaParser = uiSchemaParser,
      getDataSetsFromDisk = getDataSetsFromDisk,
      organizationRepository = organizationRepository,
      mgoByteArrayStorage = mgoByteArrayStorage,
    )

  @Before
  fun setup() =
    runTest {
      jvmQuickJsRepository.create()
      organizationRepository.deleteAll()
    }

  @Test
  fun testInvoke() =
    runTest {
      // Given: A organization is stored
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: The lifestyle category
      val category = getHealthCategoriesFromDisk().map { group -> group.categories }.flatten().first { category -> category.id == "lifestyle" }

      // Given: Five fhir responses that are part of the lifestyle category are cached
      val livingSitutationJson = readResourceFile("livingSituation.json").toByteArray()
      mgoByteArrayStorage.save("livingSituation.json", livingSitutationJson)
      val alcoholUseJson = readResourceFile("alcoholUse.json").toByteArray()
      mgoByteArrayStorage.save("alcoholUse.json", alcoholUseJson)
      val drugUseJson = readResourceFile("drugUse.json").toByteArray()
      mgoByteArrayStorage.save("drugUse.json", drugUseJson)
      val tobaccoUseJson = readResourceFile("tobaccoUse.json").toByteArray()
      mgoByteArrayStorage.save("tobaccoUse.json", tobaccoUseJson)
      val nutritionAdvice = readResourceFile("nutritionAdvice.json").toByteArray()
      mgoByteArrayStorage.save("nutritionAdvice.json", nutritionAdvice)

      val fhirRequest =
        FhirRequest(
          organizationId = "1",
          dataServiceId = "48",
          endpointId = "1",
          medmijId = "",
          endpointPath = "",
          resourceEndpoint = "",
          fhirVersion = FhirVersion.R3,
          url = "",
        )
      val fhirResponses =
        listOf(
          FhirResponse.Success(
            request = fhirRequest,
            cacheKey = "livingSituation.json",
            isEmpty = false,
          ),
          FhirResponse.Success(
            request = fhirRequest,
            cacheKey = "alcoholUse.json",
            isEmpty = false,
          ),
          FhirResponse.Success(
            request = fhirRequest,
            cacheKey = "drugUse.json",
            isEmpty = false,
          ),
          FhirResponse.Success(
            request = fhirRequest,
            cacheKey = "tobaccoUse.json",
            isEmpty = false,
          ),
          FhirResponse.Success(
            request = fhirRequest,
            cacheKey = "nutritionAdvice.json",
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
}
