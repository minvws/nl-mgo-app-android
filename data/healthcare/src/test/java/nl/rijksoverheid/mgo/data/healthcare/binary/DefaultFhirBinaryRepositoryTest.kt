package nl.rijksoverheid.mgo.data.healthcare.binary

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.api.dva.BinaryResponse
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestCacheFileStore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

internal class DefaultFhirBinaryRepositoryTest {
  private val cacheFileStore = TestCacheFileStore()
  private val dvaApi: DvaApi = mockk<DvaApi>()
  private val repository =
    DefaultFhirBinaryRepository(
      cacheFileStore = cacheFileStore,
      dvaApi = dvaApi,
    )

  @Test
  fun testDownloadSuccess() =
    runTest {
      // Given: binary download success
      coEvery { dvaApi.binary(resourceEndpoint = "", fhirBinary = "") } answers {
        BinaryResponse(
          id = "example.pdf",
          contentType = "application/pdf",
          content = "SGVsbG8gV29ybGQ=",
        )
      }

      // When: calling download
      val healthCareBinary = repository.download(resourceEndpoint = "", fhirBinary = "")

      // Then: File is saved
      assertTrue(cacheFileStore.assertFileSaved())

      // Then: Correct binary is returned
      val expectedFhirBinary = FhirBinary(file = File(""), contentType = "application/pdf")
      assertEquals(Result.success(expectedFhirBinary), healthCareBinary)
    }

  @Test
  fun testDownloadFailed() =
    runTest {
      // Given: binary download failed
      val error = HttpException(Response.error<ResponseBody>(500, "some content".toResponseBody("plain/text".toMediaTypeOrNull())))
      coEvery { dvaApi.binary(resourceEndpoint = "", fhirBinary = "") } answers {
        throw error
      }

      // When: calling download
      val healthCareBinary = repository.download(resourceEndpoint = "", fhirBinary = "")

      // Then: Correct binary is returned
      assertEquals(Result.failure<FhirBinary>(error), healthCareBinary)
    }

  @Test
  fun testCleanup() =
    runTest {
      // Given: binary download success
      coEvery { dvaApi.binary(resourceEndpoint = "", fhirBinary = "") } answers {
        BinaryResponse(
          id = "example.pdf",
          contentType = "application/pdf",
          content = "SGVsbG8gV29ybGQ=",
        )
      }

      // Given: File is downloaded
      repository.download(resourceEndpoint = "", fhirBinary = "")

      // When: Calling cleanup
      repository.cleanup()

      // Then: Attachment is removed
      assertTrue(cacheFileStore.assertNoFilesSaved())
    }
}
