package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_REQUEST
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_SERVER_ERROR
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_SUCCESS
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_USER_ERROR
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS
import org.junit.Test

internal class RetryFailedRequestsTest {
  private val getRequests = mockk<GetRequests>(relaxed = true)
  private val fhirRepository = mockk<FhirRepository>(relaxed = true)
  private val usecase = RetryFailedRequests(getRequests = getRequests, fhirRepository = fhirRepository)

  @Test
  fun testInvoke() =
    runTest {
      // Given: One succesful responses and two failed
      val responses = listOf(TEST_FHIR_RESPONSE_SUCCESS(), TEST_FHIR_RESPONSE_USER_ERROR, TEST_FHIR_RESPONSE_SERVER_ERROR)

      // Given: Requests are returned for category
      every { getRequests.invoke(organizations = listOf(), categories = listOf(TEST_HEALTH_CATEGORY_PROBLEMS)) } answers { listOf(TEST_FHIR_REQUEST) }

      // Given: Fhir repository returns these responses
      every { fhirRepository.observe() } answers { flowOf(responses) }

      // When: Calling invoke
      usecase.invoke(category = TEST_HEALTH_CATEGORY_PROBLEMS, organizations = listOf())

      // Then: Responses are retried
      val requests = listOf(TEST_FHIR_RESPONSE_USER_ERROR.request, TEST_FHIR_RESPONSE_SERVER_ERROR.request)
      coVerify(exactly = 1) { fhirRepository.retry(requests) }
    }
}
