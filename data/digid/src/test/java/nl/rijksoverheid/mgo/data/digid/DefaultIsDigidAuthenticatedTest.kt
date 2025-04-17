package nl.rijksoverheid.mgo.data.digid

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_DIGID_AUTHENTICATED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertEquals
import org.junit.Test

internal class DefaultIsDigidAuthenticatedTest {
  private val keyValueStore = TestKeyValueStore()

  @Test
  fun testIsAuthenticated() =
    runTest {
      // Given: Authenticated is false
      keyValueStore.setBoolean(KEY_DIGID_AUTHENTICATED, false)

      // When: Calling use case
      val usecase = DefaultIsDigidAuthenticated(keyValueStore = keyValueStore)
      val isAuthenticated = usecase.invoke()

      // Then: Authenticated is false
      assertEquals(false, isAuthenticated)
    }
}
