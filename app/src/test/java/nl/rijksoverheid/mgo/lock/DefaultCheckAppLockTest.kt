package nl.rijksoverheid.mgo.lock

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_CLOSED_TIMESTAMP
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import kotlinx.coroutines.test.runTest

internal class DefaultCheckAppLockTest {
    @Test
    fun `Given app not closed, When calling use case, Return false`() =
        runTest {
            // Given
            val keyValueStore = TestKeyValueStore()
            val clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC)
            val usecase = DefaultAppLocked(clock = clock, keyValueStore = keyValueStore)

            // When
            val appLocked = usecase.invoke()

            // Then
            assertEquals(false, appLocked)
        }

    @Test
    fun `Given app closed for 1 minute, When calling use case, Return false`() =
        runTest {
            // Given
            val keyValueStore = TestKeyValueStore()
            val clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC)
            val appClosedTimestamp = Instant.parse("2000-01-01T10:00:00.00Z")
            keyValueStore.setLong(KEY_APP_CLOSED_TIMESTAMP, appClosedTimestamp.epochSecond)
            val usecase = DefaultAppLocked(clock = clock, keyValueStore = keyValueStore)

            // When
            val appLocked = usecase.invoke()

            // Then
            assertEquals(false, appLocked)
        }

    @Test
    fun `Given app closed for 2 minutes, When calling use case, Return true`() =
        runTest {
            // Given
            val keyValueStore = TestKeyValueStore()
            val clock = Clock.fixed(Instant.parse("2000-01-01T10:02:00.00Z"), ZoneOffset.UTC)
            val appClosedTimestamp = Instant.parse("2000-01-01T10:00:00.00Z")
            keyValueStore.setLong(KEY_APP_CLOSED_TIMESTAMP, appClosedTimestamp.epochSecond)
            val usecase = DefaultAppLocked(clock = clock, keyValueStore = keyValueStore)

            // When
            val appLocked = usecase.invoke()

            // Then
            assertEquals(true, appLocked)
        }

    @Test
    fun `Given app closed for 3 minutes, When calling use case, Return true`() =
        runTest {
            // Given
            val keyValueStore = TestKeyValueStore()
            val clock = Clock.fixed(Instant.parse("2000-01-01T10:03:00.00Z"), ZoneOffset.UTC)
            val appClosedTimestamp = Instant.parse("2000-01-01T10:00:00.00Z")
            keyValueStore.setLong(KEY_APP_CLOSED_TIMESTAMP, appClosedTimestamp.epochSecond)
            val usecase = DefaultAppLocked(clock = clock, keyValueStore = keyValueStore)

            // When
            val appLocked = usecase.invoke()

            // Then
            assertEquals(true, appLocked)
        }
}
