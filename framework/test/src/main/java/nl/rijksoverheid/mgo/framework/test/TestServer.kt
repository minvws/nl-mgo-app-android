package nl.rijksoverheid.mgo.framework.test

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

typealias TestServerBody = String

class TestServer {
    private var server: MockWebServer? = null

    fun start(): MockWebServer {
        server?.shutdown()
        val server = MockWebServer()
        this.server = server
        server.start()
        return server
    }

    fun url(): String {
        return requireNotNull(server?.url("/").toString())
    }

    fun enqueue200() {
        server?.enqueue(MockResponse().setResponseCode(200))
    }

    fun enqueue500() {
        server?.enqueue(MockResponse().setResponseCode(500))
    }

    fun enqueueJson(json: TestServerBody) {
        server?.enqueue(MockResponse().setBody(json))
    }

    fun getRequest(): RecordedRequest? {
        return server?.takeRequest()
    }

    fun stop() {
        server?.shutdown()
    }
}

/**
 * Helper method to load json from the resources folder for a unit test.
 * Useful for example unit tests where you want to load local json files into a mock web server.
 */
fun getTestServerBodyForUnitTest(filePath: String): TestServerBody {
    return getJsonFromResources(filePath)
}
