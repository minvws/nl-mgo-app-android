package nl.rijksoverheid.mgo.framework.test

import org.junit.rules.ExternalResource

class TestServerRule : ExternalResource() {
    val testServer = TestServer()

    override fun before() {
        testServer.start()
    }

    override fun after() {
        testServer.stop()
    }
}
