package nl.rijksoverheid.mgo.framework.storage

import androidx.datastore.preferences.core.booleanPreferencesKey

internal val KEY_HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")

interface KeyValueStore {
    fun setBoolean(value: Boolean)

    fun getBoolean()
}
