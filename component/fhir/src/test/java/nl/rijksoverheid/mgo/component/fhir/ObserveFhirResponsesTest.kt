package nl.rijksoverheid.mgo.component.fhir

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.fhir.FhirRepositoryRule
import nl.rijksoverheid.mgo.data.fhir.FhirResponseJson
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_LIFESTYLE
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ObserveFhirResponsesTest {
  private val byteArrayStorage = MemoryMgoByteArrayStorage()
  private val getDataSetsFromDisk = JvmGetDataSetsFromDisk()
  private val getEndpoints = GetEndpoints(getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk))

  @get:Rule
  val fhirRepositoryRule = FhirRepositoryRule(byteArrayStorage)

  private lateinit var observeFhirResponses: ObserveFhirResponses

  @Before
  fun setup() {
    observeFhirResponses =
      ObserveFhirResponses(
        getEndpoints = getEndpoints,
        fhirRepository = fhirRepositoryRule.getRepository(),
      )
  }

  @Test
  fun testInvoke() =
    runTest {
      // Given: All lifestyle responses are success
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.DRUG_USE, organizationId = TEST_MGO_ORGANIZATION.id, endpointId = "drugUse")
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.ALCOHOL_USE, organizationId = TEST_MGO_ORGANIZATION.id, endpointId = "alcoholUse")
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.LIVING_SITUATION,
        organizationId = TEST_MGO_ORGANIZATION.id,
        endpointId = "livingSituation",
      )
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.NUTRITION_ADVICE,
        organizationId = TEST_MGO_ORGANIZATION.id,
        endpointId = "nutritionAdvice",
      )
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.TOBACCO_USE, organizationId = TEST_MGO_ORGANIZATION.id, endpointId = "tobaccoUse")

      // When: Calling use case for lifestyle category
      val fhirResponsesFlow = observeFhirResponses.invoke(categories = listOf(TEST_HEALTH_CATEGORY_LIFESTYLE), organizations = listOf(TEST_MGO_ORGANIZATION))

      // Then: Fhir responses are returned
      fhirResponsesFlow.test {
        val responses = awaitItem()
        assertEquals(5, responses.size)
      }
    }
}
