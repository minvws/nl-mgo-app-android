package nl.rijksoverheid.mgo.framework.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

private const val TEST_DATASTORE_NAME = "test_datastore"

internal class DataStoreKeyValueStoreTest {
    @Test
    fun validateBoolean() =
        runTest {
            // Given
            val context = ApplicationProvider.getApplicationContext<Context>()
            val preferenceKey = booleanPreferencesKey("test")
            val dataStore = createDataStore(context = context, scope = this)
            val keyValueStore = DataStoreKeyValueStore(dataStore = dataStore)

            // When
            keyValueStore.setBoolean(preferenceKey, true)

            // Then
            assertTrue(keyValueStore.getBoolean(preferenceKey))
        }
}

private fun createDataStore(
    context: Context,
    scope: TestScope,
): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        scope = scope,
        produceFile = { context.preferencesDataStoreFile(TEST_DATASTORE_NAME) },
    )
}
