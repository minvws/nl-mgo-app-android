package nl.rijksoverheid.mgo.data.pincode

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.runBlocking

internal class DefaultHasSeenPinCodeTest {
    @Test
    fun `Given the key value store, When setting the has seen pin code flag to false, Then return false`() {
        // Given
        val keyValueStore = TestKeyValueStore()
        val hasSeenOnboarding = DefaultHasSeenPinCode(keyValueStore = keyValueStore)

        // When
        runBlocking { keyValueStore.setBoolean(KEY_HAS_SEEN_PIN_CODE, false) }

        // Then
        assertFalse(hasSeenOnboarding.invoke())
    }

    @Test
    fun `Given the key value store, When setting the has seen pin code flag to true, Then return true`() {
        // Given
        val keyValueStore = TestKeyValueStore()
        val hasSeenOnboarding = DefaultHasSeenPinCode(keyValueStore = keyValueStore)

        // When
        runBlocking { keyValueStore.setBoolean(KEY_HAS_SEEN_PIN_CODE, true) }

        // Then
        assertTrue(hasSeenOnboarding.invoke())
    }
}
