package nl.rijksoverheid.mgo.lock

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_CLOSED_TIMESTAMP
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

internal class DefaultCheckAppLockTest {
  private val keyValueStore = TestKeyValueStore()
  private val secureKeyValueStore = TestKeyValueStore()

  @Test
  fun testNoPinAndAppNotClosed() =
    runTest {
      // Given: No pin code and app not closed
      val clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC)
      val usecase = DefaultAppLocked(clock = clock, keyValueStore = keyValueStore, secureKeyValueStore = secureKeyValueStore)

      // When: Calling use case
      val appLocked = usecase.invoke()

      // Then: App is not locked
      assertEquals(false, appLocked)
    }

  @Test
  fun testAppNotClosed() =
    runTest {
      // Given: App not closed
      val clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC)
      secureKeyValueStore.setString(KEY_PIN_CODE, "123")
      val usecase = DefaultAppLocked(clock = clock, keyValueStore = keyValueStore, secureKeyValueStore = secureKeyValueStore)

      // When: Calling use case
      val appLocked = usecase.invoke()

      // Then: App is not locked
      assertEquals(false, appLocked)
    }

  @Test
  fun testAppClosedOneMinute() =
    runTest {
      // Given: App closed for one minute
      val clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC)
      val appClosedTimestamp = Instant.parse("2000-01-01T10:00:00.00Z")
      keyValueStore.setLong(KEY_APP_CLOSED_TIMESTAMP, appClosedTimestamp.epochSecond)
      secureKeyValueStore.setString(KEY_PIN_CODE, "123")
      val usecase = DefaultAppLocked(clock = clock, keyValueStore = keyValueStore, secureKeyValueStore = secureKeyValueStore)

      // When: Calling use case
      val appLocked = usecase.invoke()

      // Then: App is not locked
      assertEquals(false, appLocked)
    }

  @Test
  fun testAppClosedTwoMinutes() =
    runTest {
      // Given: App closed for two minutes
      val clock = Clock.fixed(Instant.parse("2000-01-01T10:02:00.00Z"), ZoneOffset.UTC)
      val appClosedTimestamp = Instant.parse("2000-01-01T10:00:00.00Z")
      keyValueStore.setLong(KEY_APP_CLOSED_TIMESTAMP, appClosedTimestamp.epochSecond)
      secureKeyValueStore.setString(KEY_PIN_CODE, "123")
      val usecase = DefaultAppLocked(clock = clock, keyValueStore = keyValueStore, secureKeyValueStore = secureKeyValueStore)

      // When: Calling use case
      val appLocked = usecase.invoke()

      // Then: App is locked
      assertEquals(true, appLocked)
    }

  @Test
  fun testClosedAppThreeMinutes() =
    runTest {
      // Given: App closed for three minutes
      val clock = Clock.fixed(Instant.parse("2000-01-01T10:03:00.00Z"), ZoneOffset.UTC)
      val appClosedTimestamp = Instant.parse("2000-01-01T10:00:00.00Z")
      keyValueStore.setLong(KEY_APP_CLOSED_TIMESTAMP, appClosedTimestamp.epochSecond)
      secureKeyValueStore.setString(KEY_PIN_CODE, "123")
      val usecase = DefaultAppLocked(clock = clock, keyValueStore = keyValueStore, secureKeyValueStore = secureKeyValueStore)

      // When: Calling use case
      val appLocked = usecase.invoke()

      // Then: App is locked
      assertEquals(true, appLocked)
    }
}
