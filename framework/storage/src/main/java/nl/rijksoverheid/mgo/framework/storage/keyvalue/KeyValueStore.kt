package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

val KEY_HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")
val KEY_HAS_SEEN_PIN_CODE = booleanPreferencesKey("has_seen_pin_code")
val KEY_IS_ROOT_CHECKED = booleanPreferencesKey("is_root_checked")

interface KeyValueStore {
    suspend fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    )

    suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean
}
