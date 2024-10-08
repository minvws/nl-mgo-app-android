package nl.rijksoverheid.mgo.data.pincode

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.runBlocking

internal class DefaultSetHasSeenPinCodeTest {
    @Test
    fun `Given the key value store, When setting the pin code flag to false, Then store has that value`() {
        // Given
        val keyValueStore = TestKeyValueStore()
        val setHasSeenPinCode = DefaultSetHasSeenPinCode(keyValueStore = keyValueStore)

        // When
        setHasSeenPinCode.invoke(false)

        // Then
        assertFalse(runBlocking { keyValueStore.getBoolean(KEY_HAS_SEEN_PIN_CODE) })
    }

    @Test
    fun `Given the key value store, When setting the pin code flag to true, Then store has that value`() {
        // Given
        val keyValueStore = TestKeyValueStore()
        val setHasPinCode = DefaultSetHasSeenPinCode(keyValueStore = keyValueStore)

        // When
        setHasPinCode.invoke(true)

        // Then
        assertTrue(runBlocking { keyValueStore.getBoolean(KEY_HAS_SEEN_PIN_CODE) })
    }
}
