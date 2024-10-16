package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

val KEY_HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")
val KEY_PIN_CODE = stringPreferencesKey("pin_code")
val LOGIN_WITH_BIOMETRIC_ENABLED = booleanPreferencesKey("login_with_biometric_enabled")
val KEY_IS_ROOT_CHECKED = booleanPreferencesKey("is_root_checked")

interface KeyValueStore {
    suspend fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    )

    suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean

    suspend fun setString(
        key: Preferences.Key<String>,
        value: String,
    )

    suspend fun getString(key: Preferences.Key<String>): String?
}
