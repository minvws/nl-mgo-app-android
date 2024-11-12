package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.security.crypto.MasterKeys
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class EncryptedSharedPreferencesSecureKeyValueStoreTest {
    @Test
    fun validateBoolean() =
        runTest {
            // Given
            val preferenceKey1 = booleanPreferencesKey("test1")
            val preferenceKey2 = booleanPreferencesKey("test2")
            val keyValueStore = createKeyValueStore()

            // When
            keyValueStore.setBoolean(preferenceKey1, true)
            keyValueStore.setBoolean(preferenceKey2, true)
            keyValueStore.removeBoolean(preferenceKey2)

            // Then
            assertTrue(keyValueStore.getBoolean(preferenceKey1))
            assertFalse(keyValueStore.getBoolean(preferenceKey2))
        }

    @Test
    fun validateString() =
        runTest {
            // Given
            val preferenceKey1 = stringPreferencesKey("test1")
            val preferenceKey2 = stringPreferencesKey("test2")
            val keyValueStore = createKeyValueStore()

            // When
            keyValueStore.setString(preferenceKey1, "123")
            keyValueStore.setString(preferenceKey2, "123")
            keyValueStore.removeString(preferenceKey2)

            // Then
            assertEquals("123", keyValueStore.getString(preferenceKey1))
            assertNull(keyValueStore.getString(preferenceKey2))
        }

    @Test
    fun validateClear() =
        runTest {
            // Given
            val preferenceKey = booleanPreferencesKey("test")
            val keyValueStore = createKeyValueStore()
            keyValueStore.setBoolean(preferenceKey, true)

            // When
            keyValueStore.clear()

            // Then
            assertEquals(false, keyValueStore.getBoolean(preferenceKey))
        }
}

private fun createKeyValueStore(): EncryptedSharedPreferencesSecureKeyValueStore {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    return EncryptedSharedPreferencesSecureKeyValueStore(context = context, masterKeyAlias = masterKeyAlias)
}
