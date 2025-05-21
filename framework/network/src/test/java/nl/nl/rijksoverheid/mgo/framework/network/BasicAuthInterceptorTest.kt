package nl.nl.rijksoverheid.mgo.framework.network

import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.Credentials.basic
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class BasicAuthInterceptorTest {
  @get:Rule
  val testServerRule = TestServerRule()

  private val testServer = testServerRule.testServer

  @Test
  fun `Given response, When making successful request, Then correct headers are sent`() {
    // Given
    testServer.enqueue200()
    val user = "test"
    val password = "123"

    // When
    val interceptor = BasicAuthInterceptor(user = user, password = password)
    val client =
      OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    val request =
      Request.Builder()
        .url(testServer.url())
        .build()
    client.newCall(request).execute()

    // Then
    val recordedRequest = testServer.getRequest()
    assertEquals(basic(username = user, password = password), recordedRequest?.getHeader("Authorization"))
  }
}
