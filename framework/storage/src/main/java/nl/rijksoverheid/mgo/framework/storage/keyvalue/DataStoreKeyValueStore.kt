package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val KEY_HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")
val KEY_LOGIN_WITH_BIOMETRIC_ENABLED = booleanPreferencesKey("login_with_biometric_enabled")
val KEY_IS_ROOT_CHECKED = booleanPreferencesKey("is_root_checked")

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
            }.first() == true
        }
    }

    override suspend fun removeBoolean(key: Preferences.Key<Boolean>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
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

    override suspend fun removeString(key: Preferences.Key<String>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
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
