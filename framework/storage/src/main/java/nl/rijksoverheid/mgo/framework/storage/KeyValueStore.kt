package nl.rijksoverheid.mgo.framework.storage

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

val KEY_HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")

interface KeyValueStore {
    fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    )

    fun getBoolean(key: Preferences.Key<Boolean>): Boolean
}
