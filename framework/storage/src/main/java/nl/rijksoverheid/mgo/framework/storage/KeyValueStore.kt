package nl.rijksoverheid.mgo.framework.storage

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

val KEY_HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")

interface KeyValueStore {
    suspend fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    )

    suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean
}
