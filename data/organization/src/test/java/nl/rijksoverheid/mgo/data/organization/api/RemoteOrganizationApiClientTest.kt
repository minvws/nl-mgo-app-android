package nl.rijksoverheid.mgo.data.organization.api

import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.File

class RemoteOrganizationApiClientTest {
  @get:Rule
  val testServerRule = TestServerRule()

  private val cacheDir = File(System.getProperty("java.io.tmpdir"), "okhttp-cache")

  @After
  fun cleanup() {
    cacheDir.deleteRecursively()
  }

  @Test
  fun testGetOrganizationsSuccess() {
    // Given: OkHttp
    val okHttpClient = getOkHttpClient()

    // Given: Organizations succeed from api
    val organizationsJson =
      javaClass.classLoader!!
        .getResourceAsStream("benchmark-organizations-new.json")
        .bufferedReader()
        .use { it.readText() }
    testServerRule.testServer.enqueueJson(
      organizationsJson,
      headers =
        listOf(
          Pair("Cache-Control", "public, max-age=300"),
        ),
    )
    testServerRule.testServer.enqueueJson(
      organizationsJson,
      headers =
        listOf(
          Pair("Cache-Control", "public, max-age=300"),
        ),
    )

    // Given: Api client
    val repository =
      RemoteOrganizationApiClient(
        okHttpClient = okHttpClient,
        organizationsUrl = testServerRule.testServer.url(),
        endpointsUrl = testServerRule.testServer.url(),
      )

    // When: Calling get organizations
    val result = repository.getOrganizations()

    // Then
    assertTrue(result.isSuccess)
    val response = result.getOrNull()!!
    assertFalse(response.cached)
    // Important: we need to do something with the InputStream, else OkHttp does not cache the response
    assertNotNull(response.response.readAllBytes())

    // When: Calling get organizations again
    val result2 = repository.getOrganizations()

    // Then
    assertTrue(result2.isSuccess)
    val response2 = result2.getOrNull()!!
    assertTrue(response2.cached)
  }

  @Test
  fun testGetOrganizationsFailed() {
    // Given: OkHttp
    val okHttpClient = getOkHttpClient()

    // Given: Organizations fail from api
    testServerRule.testServer.enqueue500()

    // Given: Api client
    val repository =
      RemoteOrganizationApiClient(
        okHttpClient = okHttpClient,
        organizationsUrl = testServerRule.testServer.url(),
        endpointsUrl = testServerRule.testServer.url(),
      )

    // When: Calling get organizations
    val result = repository.getOrganizations()

    // Then
    assertTrue(result.isFailure)
  }

  @Test
  fun testGetEndpointsSuccess() {
    // Given: OkHttp
    val okHttpClient = getOkHttpClient()

    // Given: Endpoints succeed from api
    val endpointsJson =
      javaClass.classLoader!!
        .getResourceAsStream("benchmark-endpoints.json")
        .bufferedReader()
        .use { it.readText() }
    testServerRule.testServer.enqueueJson(
      endpointsJson,
      headers =
        listOf(
          Pair("Cache-Control", "public, max-age=300"),
        ),
    )
    testServerRule.testServer.enqueueJson(
      endpointsJson,
      headers =
        listOf(
          Pair("Cache-Control", "public, max-age=300"),
        ),
    )

    // Given: Api client
    val repository =
      RemoteOrganizationApiClient(
        okHttpClient = okHttpClient,
        organizationsUrl = testServerRule.testServer.url(),
        endpointsUrl = testServerRule.testServer.url(),
      )

    // When: Calling get endpoints
    val result = repository.getEndpoints()

    // Then
    assertTrue(result.isSuccess)
    val response = result.getOrNull()!!
    assertFalse(response.cached)
    // Important: we need to do something with the InputStream, else OkHttp does not cache the response
    assertNotNull(response.response.readAllBytes())

    // When: Calling get endpoints again
    val result2 = repository.getEndpoints()

    // Then
    assertTrue(result2.isSuccess)
    val response2 = result2.getOrNull()!!
    assertTrue(response2.cached)
  }

  @Test
  fun testGetEndpointsFailed() {
    // Given: OkHttp
    val okHttpClient = getOkHttpClient()

    // Given: Endpoints fail from api
    testServerRule.testServer.enqueue500()

    // Given: Api client
    val repository =
      RemoteOrganizationApiClient(
        okHttpClient = okHttpClient,
        organizationsUrl = testServerRule.testServer.url(),
        endpointsUrl = testServerRule.testServer.url(),
      )

    // When: Calling get endpoints
    val result = repository.getEndpoints()

    // Then
    assertTrue(result.isFailure)
  }

  private fun getOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val cache =
      Cache(
        cacheDir,
        50L * 1024 * 1024,
      )
    builder.cache(cache)
    return builder.build()
  }
}
