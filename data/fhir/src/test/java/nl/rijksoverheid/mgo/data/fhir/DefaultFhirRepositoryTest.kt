package nl.rijksoverheid.mgo.data.fhir

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import io.mockk.InternalPlatformDsl.toStr
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import nl.rijksoverheid.mgo.framework.storage.MemoryMgoStorage
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultFhirRepositoryTest {
  @get:Rule
  val testServerRule = TestServerRule()

  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val okHttpClient = OkHttpClient.Builder().build()
  private val testServer = testServerRule.testServer
  private val fileStorage = MemoryMgoStorage()
  private val repository = DefaultFhirRepository(okHttpClient = okHttpClient, mgoStorage = fileStorage, context = context)

  @Test
  fun testFetchSuccess() =
    runTest {
      // Given: Request success
      val alcoholUseJson = readResourceFile("alcoholUse.json")
      testServer.enqueueJson(alcoholUseJson)

      // When: Calling fetch
      repository.fetch(
        organizationId = "1",
        dataServiceId = "1",
        endpointId = "alcoholUse",
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        url = testServer.url(),
      )

      // Fhir response is stored
      val expectedStored = fileStorage.get("1/1/alcoholUse.json")
      assertEquals(expectedStored, fileStorage.get("1/1/alcoholUse.json"))

      // Fhir response is emitted in flow and can be observed
      repository.observe(organizationId = "1", dataServiceId = "1", endpointId = "alcoholUse").test {
        val expectedEmit =
          FhirResponse.Success(
            organizationId = "1",
            dataServiceId = "1",
            endpointId = "alcoholUse",
            cacheKey = "1/1/alcoholUse.json",
            isEmpty = false,
          )
        assertEquals(expectedEmit, awaitItem())
      }
    }

  @Test
  fun testFetchEmpty() =
    runTest {
      // Given: Request success with empty bundle
      val emptyBundleJson = readResourceFile("emptyBundle.json")
      testServer.enqueueJson(emptyBundleJson)

      // When: Calling fetch
      repository.fetch(
        organizationId = "1",
        dataServiceId = "1",
        endpointId = "emptyBundle",
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        url = testServer.url(),
      )

      // Fhir response is stored
      val expectedStored = fileStorage.get("1/1/emptyBundle.json")
      assertEquals(expectedStored, fileStorage.get("1/1/emptyBundle.json"))

      // Fhir response is emitted in flow and can be observed
      repository.observe(organizationId = "1", dataServiceId = "1", endpointId = "emptyBundle").test {
        val expectedEmit =
          FhirResponse.Success(
            organizationId = "1",
            dataServiceId = "1",
            endpointId = "emptyBundle",
            cacheKey = "1/1/emptyBundle.json",
            isEmpty = true,
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
      assertNull(fileStorage.get("1/1/1.json"))

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
      val alcoholUseJson = readResourceFile("alcoholUse.json")
      testServer.enqueueJson(alcoholUseJson)

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

  @Test
  fun testDelete() =
    runTest {
      // Given: Fhir response is fetched
      val alcoholUseJson = readResourceFile("alcoholUse.json")
      testServer.enqueueJson(alcoholUseJson)
      repository.fetch(
        organizationId = "1",
        dataServiceId = "1",
        endpointId = "1",
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        url = testServer.url(),
      )

      // When: Calling delete for organization
      repository.delete("1")

      // Then: Fhir response is no longer cached
      assertNull(fileStorage.get("1/1/1.json"))
    }

  @Test
  fun testFetchBinarySuccess() =
    runTest {
      // Given: Request returns response
      val responseJson =
        buildJsonObject {
          put("id", "file")
          put("content", "SGVsbG8gV29ybGQ=")
          put("contentType", "application/pdf")
        }
      testServer.enqueueJson(responseJson.toStr())

      // When: calling fetch binary
      val result =
        repository.fetchBinary(
          resourceEndpoint = "",
          url = testServer.url(),
        )

      // Then: Success result is returned
      assertTrue(result.isSuccess)
      assertEquals("file.pdf", result.getOrNull()?.file?.name)
      assertEquals("application/pdf", result.getOrNull()?.contentType)
    }

  @Test
  fun testFetchBinaryFailure() =
    runTest {
      // Given: Request fails
      testServer.enqueue500()

      // When: calling fetch binary
      val result =
        repository.fetchBinary(
          resourceEndpoint = "",
          url = testServer.url(),
        )

      // Then: Failure result is returned
      assertTrue(result.isFailure)
    }
}
