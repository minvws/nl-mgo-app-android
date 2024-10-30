package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

internal class DataStoreKeyValueStore(
    private val dataStore: DataStore<Preferences>,
) : KeyValueStore {
    override suspend fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    ) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[key]
            }.first() ?: false
        }
    }

    override suspend fun setString(
        key: Preferences.Key<String>,
        value: String,
    ) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun getString(key: Preferences.Key<String>): String? {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[key]
            }.firstOrNull()
        }
    }

    override fun clear() {
        runBlocking {
            dataStore.edit {
                it.clear()
            }
        }
    }
}
