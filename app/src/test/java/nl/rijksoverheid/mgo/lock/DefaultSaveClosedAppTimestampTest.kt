package nl.rijksoverheid.mgo.lock

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_CLOSED_TIMESTAMP
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

internal class DefaultSaveClosedAppTimestampTest {
  @Test
  fun `Given timestamp, When calling use case, Save the timestamp`() =
    runTest {
      // Given
      val keyValueStore = TestKeyValueStore()
      val clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC)
      val usecase = DefaultSaveClosedAppTimestamp(clock = clock, keyValueStore = keyValueStore)

      // When
      usecase.invoke()

      // Then
      assertEquals(946720860L, keyValueStore.getLong(KEY_APP_CLOSED_TIMESTAMP))
    }
}
