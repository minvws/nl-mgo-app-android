package nl.rijksoverheid.mgo.framework.test

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

typealias TestServerBody = String

/**
 * Setup a test server that intercepts traffic executed through OkHttp.
 */
class TestServer {
  private var server: MockWebServer? = null

  /**
   * Start the mock web server.
   */
  fun start(): MockWebServer {
    server?.shutdown()
    val server = MockWebServer()
    this.server = server
    server.start()
    return server
  }

  /**
   * Get the url of the mock web server. Be sure to fire requests to this base url.
   */
  fun url(): String = requireNotNull(server?.url("/").toString())

  fun setJson(
    json: TestServerBody,
    responseCode: Int,
  ) {
    server?.dispatcher =
      object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse =
          MockResponse()
            .setBody(json)
            .setResponseCode(responseCode)
      }
  }

  /**
   * Enqueue 200 responses. When calling this, the next request fired through OkHttp will receive a 200 response with empty body.
   *
   * @param amount The amount of requests to enqueue.
   */
  fun enqueue200(amount: Int = 1) {
    for (i in 0 until amount) {
      server?.enqueue(MockResponse().setResponseCode(200))
    }
  }

  /**
   * Enqueue 500 responses. When calling this, the next request fired through OkHttp will receive a 500 response with empty body.
   *
   * @param amount The amount of requests to enqueue.
   */
  fun enqueue500(amount: Int = 1) {
    for (i in 0 until amount) {
      server?.enqueue(MockResponse().setResponseCode(500))
    }
  }

  /**
   * Enqueue a response. When calling this, the next request fired through OkHttp will receive this response.
   *
   * @param json The json body to return.
   */
  fun enqueueJson(json: TestServerBody) {
    server?.enqueue(MockResponse().setBody(json))
  }

  /**
   * Get the latest response of the mock web server.
   *
   * @return The [RecordedRequest].
   */
  fun getRequest(): RecordedRequest? = server?.takeRequest()

  /**
   * Stops the mock web server.
   */
  fun stop() {
    server?.shutdown()
  }
}
