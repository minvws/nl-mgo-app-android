package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest

internal class DataStoreKeyValueStoreTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "test")
    }

    @Test
    fun validateBoolean() =
        runTest {
            // Given
            val preferenceKey = booleanPreferencesKey("test")
            val keyValueStore = DataStoreKeyValueStore(dataStore = context.dataStore)

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
            val keyValueStore = DataStoreKeyValueStore(dataStore = context.dataStore)

            // When
            runBlocking { keyValueStore.setString(preferenceKey, "123") }

            // Then
            assertEquals("123", runBlocking { keyValueStore.getString(preferenceKey) })
        }

    @Test
    fun validateClear() =
        runTest {
            // Given
            val preferenceKey = booleanPreferencesKey("test")
            val keyValueStore = DataStoreKeyValueStore(dataStore = context.dataStore)
            keyValueStore.setBoolean(preferenceKey, true)

            // When
            keyValueStore.clear()

            // Then
            assertEquals(false, keyValueStore.getBoolean(preferenceKey))
        }
}
