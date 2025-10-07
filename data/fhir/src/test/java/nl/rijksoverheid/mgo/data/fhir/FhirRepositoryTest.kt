package nl.rijksoverheid.mgo.data.fhir

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FhirRepositoryTest {
  @get:Rule
  val testServerRule = TestServerRule()

  private val okHttpClient = OkHttpClient.Builder().build()
  private val testServer = testServerRule.testServer
  private val fhirResponseJsonStore = MemoryFhirResponseJsonStore()
  private val repository = FhirRepository(okHttpClient = okHttpClient, fhirResponseJsonStore = fhirResponseJsonStore)

  @Test
  fun testFetchSuccess() =
    runTest {
      // Given: Request success
      testServer.enqueue200()

      // When: Calling fetch
      repository.fetch(
        organizationId = "1",
        dataServiceId = "1",
        endpointId = "1",
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        url = testServer.url(),
      )

      // Fhir response is stored
      val expectedStored = FhirResponseJsonSource.Memory("")
      assertEquals(expectedStored, fhirResponseJsonStore.get(organizationId = "1", dataServiceId = "1", endpointId = "1"))

      // Fhir response is emitted in flow and can be observed
      repository.observe(organizationId = "1", dataServiceId = "1", endpointId = "1").test {
        val expectedEmit =
          FhirResponse.Success(
            organizationId = "1",
            dataServiceId = "1",
            endpointId = "1",
            jsonSource = FhirResponseJsonSource.Memory(""),
          )
        assertEquals(expectedEmit, awaitItem())
      }
    }

  @Test
  fun testFetchFailure() =
    runTest {
      // Given: Request fails
      testServer.enqueue500()

      // When: Calling fetch
      repository.fetch(
        organizationId = "1",
        dataServiceId = "1",
        endpointId = "1",
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        url = testServer.url(),
      )

      // Fhir response is not stored
      assertNull(fhirResponseJsonStore.get(organizationId = "1", dataServiceId = "1", endpointId = "1"))

      // Fhir response is emitted in flow and can be observed
      repository.observe(organizationId = "1", dataServiceId = "1", endpointId = "1").test {
        val emit = awaitItem()
        assertTrue(emit is FhirResponse.Error)
      }
    }

  @Test
  fun testRetry() =
    runTest {
      // Given: Fhir response error exists
      testServer.enqueue500()
      repository.fetch(
        organizationId = "1",
        dataServiceId = "1",
        endpointId = "1",
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        url = testServer.url(),
      )

      // Given: Request success
      testServer.enqueue200()

      // When: Calling fetch
      repository.fetch(
        organizationId = "1",
        dataServiceId = "1",
        endpointId = "1",
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        url = testServer.url(),
      )

      // Fhir response is emitted in flow and can be observed
      repository.observe(organizationId = "1", dataServiceId = "1", endpointId = "1").test {
        assertTrue(awaitItem() is FhirResponse.Success)
      }
    }
}
