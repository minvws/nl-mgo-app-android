package nl.rijksoverheid.mgo.framework.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

internal class DataStoreKeyValueStore(
    @ApplicationContext private val context: Context,
) : KeyValueStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app")

    override fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    ) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }

    override fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
        return runBlocking {
            context.dataStore.data.map { preferences ->
                preferences[key]
            }.first() ?: false
        }
    }
}
