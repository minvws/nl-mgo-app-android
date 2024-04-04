package nl.rijksoverheid.mgo.framework.test

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

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

    fun enqueue(response: MockResponse) {
        server?.enqueue(response)
    }

    fun stop() {
        server?.shutdown()
    }
}
