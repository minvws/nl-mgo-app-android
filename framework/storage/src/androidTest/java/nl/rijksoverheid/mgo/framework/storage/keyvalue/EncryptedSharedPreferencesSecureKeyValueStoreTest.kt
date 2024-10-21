package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class EncryptedSharedPreferencesSecureKeyValueStoreTest {
    @Test
    fun validateBoolean() =
        runTest {
            // Given
            val preferenceKey = booleanPreferencesKey("test")
            val keyValueStore = createKeyValueStore()

            // When
            keyValueStore.setBoolean(preferenceKey, true)

            // Then
            assertTrue(keyValueStore.getBoolean(preferenceKey))
        }

    @Test
    fun validateString() =
        runTest {
            // Given
            val preferenceKey = stringPreferencesKey("test")
            val keyValueStore = createKeyValueStore()

            // When
            keyValueStore.setString(preferenceKey, "123")

            // Then
            assertEquals("123", keyValueStore.getString(preferenceKey))
        }
}

private fun createKeyValueStore(): EncryptedSharedPreferencesSecureKeyValueStore {
    val context = ApplicationProvider.getApplicationContext<Context>()
    return EncryptedSharedPreferencesSecureKeyValueStore(context)
}
