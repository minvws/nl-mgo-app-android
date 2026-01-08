package nl.rijksoverheid.mgo.component.fhir

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.fhir.FhirRepositoryRule
import nl.rijksoverheid.mgo.data.fhir.FhirResponseJson
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_ALCOHOL_USE
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_DRUG_USE
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_LIVING_SITUATION
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_NUTRITION_ADVICE
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST_TOBACCO_USE
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
  private val getRequests = GetRequests(getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk))

  @get:Rule
  val fhirRepositoryRule = FhirRepositoryRule(byteArrayStorage)

  private lateinit var observeFhirResponses: ObserveFhirResponses

  @Before
  fun setup() {
    observeFhirResponses =
      ObserveFhirResponses(
        getRequests = getRequests,
        fhirRepository = fhirRepositoryRule.getRepository(),
      )
  }

  @Test
  fun testInvoke() =
    runTest {
      // Given: All lifestyle responses are success
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.DRUG_USE, request = TEST_FHIR_REQUEST_DRUG_USE)
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.ALCOHOL_USE, request = TEST_FHIR_REQUEST_ALCOHOL_USE)
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.LIVING_SITUATION,
        request = TEST_FHIR_REQUEST_LIVING_SITUATION,
      )
      fhirRepositoryRule.enqueueSuccessResponse(
        json = FhirResponseJson.NUTRITION_ADVICE,
        request = TEST_FHIR_REQUEST_NUTRITION_ADVICE,
      )
      fhirRepositoryRule.enqueueSuccessResponse(json = FhirResponseJson.TOBACCO_USE, request = TEST_FHIR_REQUEST_TOBACCO_USE)

      // When: Calling use case for lifestyle category
      val fhirResponsesFlow = observeFhirResponses.invoke(categories = listOf(TEST_HEALTH_CATEGORY_LIFESTYLE), organizations = listOf(TEST_MGO_ORGANIZATION))

      // Then: Fhir responses are returned
      fhirResponsesFlow.test {
        val responses = awaitItem()
        assertEquals(5, responses.size)
      }
    }
}
