package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.hcimParser.JvmQuickJsRepository
import nl.rijksoverheid.mgo.data.hcimParser.javascript.JsEngineRepository
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.data.organization.createOrganizationRepositoryForJvm
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [34])
@RunWith(RobolectricTestRunner::class)
class ListItemGroupMapperTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val jvmQuickJsRepository = JvmQuickJsRepository()
  private val jsEngineRepository = JsEngineRepository(jvmQuickJsRepository)
  private val mgoResourceParser = MgoResourceParser(jsEngineRepository)
  private val uiSchemaParser = UiSchemaParser(jsEngineRepository)
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val mgoByteArrayStorage = MemoryMgoByteArrayStorage()
  private val mapper =
    ListItemGroupMapper(
      context = context,
      uiSchemaParser = uiSchemaParser,
    )
  private lateinit var organizationRepository: OrganizationRepository

  @Before
  fun setup() =
    runTest {
      jvmQuickJsRepository.create()
      organizationRepository = createOrganizationRepositoryForJvm()
    }

  @Test
  fun testInvoke() =
    runTest {
      // Given: A organization is stored
      organizationRepository.addAndSave(TEST_MGO_ORGANIZATION)

      // Given: The lifestyle category
      val category = getHealthCategoriesFromDisk().map { group -> group.categories }.flatten().first { category -> category.id == "lifestyle" }

      // Given: Five fhir responses that are part of the lifestyle category are cached
      val livingSitutationJson = readResourceFile("livingSituation.json")
      mgoByteArrayStorage.save("livingSituation.json", livingSitutationJson.toByteArray())
      val alcoholUseJson = readResourceFile("alcoholUse.json")
      mgoByteArrayStorage.save("alcoholUse.json", alcoholUseJson.toByteArray())
      val drugUseJson = readResourceFile("drugUse.json")
      mgoByteArrayStorage.save("drugUse.json", drugUseJson.toByteArray())
      val tobaccoUseJson = readResourceFile("tobaccoUse.json")
      mgoByteArrayStorage.save("tobaccoUse.json", tobaccoUseJson.toByteArray())
      val nutritionAdviceJson = readResourceFile("nutritionAdvice.json")
      mgoByteArrayStorage.save("nutritionAdvice.json", nutritionAdviceJson.toByteArray())

      val livingSitutationMgoResources = mgoResourceParser(fhirResponse = livingSitutationJson, fhirVersion = FhirVersion.R3)
      val alcoholUseMgoResources = mgoResourceParser(fhirResponse = alcoholUseJson, fhirVersion = FhirVersion.R3)
      val drugUseMgoResources = mgoResourceParser(fhirResponse = drugUseJson, fhirVersion = FhirVersion.R3)
      val tobaccoUseMgoResources = mgoResourceParser(fhirResponse = tobaccoUseJson, fhirVersion = FhirVersion.R3)
      val nutritionAdviceResources = mgoResourceParser(fhirResponse = nutritionAdviceJson, fhirVersion = FhirVersion.R3)
      val mgoResources =
        listOf(
          livingSitutationMgoResources,
          alcoholUseMgoResources,
          drugUseMgoResources,
          tobaccoUseMgoResources,
          nutritionAdviceResources,
        ).flatten()

      val mgoResourcesWithOrganization =
        mgoResources.map { mgoResource ->
          MgoResourceWithOrganization(mgoResource = mgoResource, organization = TEST_MGO_ORGANIZATION)
        }

      // When: Mapping to groups
      val groups =
        mapper.invoke(
          category = category,
          mgoResourcesWithOrganization = mgoResourcesWithOrganization,
        )

      // Then: Groups are returned with mgo resources
      assertEquals(7, groups.size)
      assertEquals(1, groups[0].items.size)
      assertEquals(1, groups[1].items.size)
      assertEquals(1, groups[2].items.size)
      assertEquals(1, groups[3].items.size)
      assertEquals(1, groups[4].items.size)
    }
}
