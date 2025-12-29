package nl.rijksoverheid.mgo.component.fhir

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhir.TestFhirRepository
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_ENDPOINT
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class FetchEndpointTest {
  private val fhirRepository = TestFhirRepository()
  private val fetchEndpoint =
    FetchEndpoint(
      clock = Clock.fixed(Instant.ofEpochSecond(0), ZoneOffset.UTC),
      fhirRepository = fhirRepository,
    )

  @Test
  fun setup() {
    fhirRepository.resetFetchAmount()
  }

  @Test
  fun testInvoke() =
    runTest {
      // Given: endpoint
      val endpoint = TEST_ENDPOINT

      // When: Calling invoke
      fetchEndpoint(endpoint = endpoint, forceRefresh = true)

      // Then: Fhir resource is fetched
      assertEquals(1, fhirRepository.getFetchAmount())
    }
}
