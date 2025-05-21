package nl.rijksoverheid.mgo.framework.test.rules

import nl.rijksoverheid.mgo.framework.test.TestServer
import org.junit.rules.ExternalResource

/**
 * JUnit rule to spin up a mock test server that intercepts traffic executed through OkHttp.
 */
class TestServerRule : ExternalResource() {
  val testServer: TestServer = TestServer()

  override fun before() {
    testServer.start()
  }

  override fun after() {
    testServer.stop()
  }
}
