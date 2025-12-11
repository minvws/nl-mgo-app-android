package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Key indicating that biometric login is enabled.
 */
val KEY_LOGIN_WITH_BIOMETRIC_ENABLED = booleanPreferencesKey("login_with_biometric_enabled")

/**
 * Key indicating that the app has checked if the device has been rooted.
 */
val KEY_IS_ROOT_CHECKED = booleanPreferencesKey("is_root_checked")

/**
 * Key indicating the timestamp the last time the app was closed (brought to background).
 */
val KEY_APP_CLOSED_TIMESTAMP = longPreferencesKey("app_closed_timestamp")

/**
 * Key indicating that the app cannot take screenshots.
 */
val KEY_FLAG_SECURE = booleanPreferencesKey("flag_secure")

/**
 * Key indicating that the app can skip the login pin code screen.
 */
val KEY_SKIP_PIN = booleanPreferencesKey("skip_pin")

/**
 * Key indicating that the app shows the automatic localisation flow instead of the manual one.
 */
val KEY_AUTOMATIC_LOCALISATION = booleanPreferencesKey("automatic_localisation")

/**
 * Key indicating that the initial values of the feature toggles have been stored.
 */
val KEY_LOCAL_FEATURE_TOGGLES_INITIALISED = booleanPreferencesKey("local_feature_toggles_initialised")

/**
 * Key indicating the the user has successfully authenticated with DigiD.
 */
val KEY_DIGID_AUTHENTICATED = booleanPreferencesKey("digid_authenticated")

val KEY_APP_THEME = stringPreferencesKey("app_theme")

/**
 * Key-value storage system that uses [Preferences] to store data.
 *
 * @param dataStore The [DataStore] to save key-values into [Preferences].
 */
internal class DataStoreKeyValueStore(
  private val dataStore: DataStore<Preferences>,
) : KeyValueStore {
  /**
   * Stores a boolean value in the key-value store.
   *
   * @param key The key associated with the boolean value.
   * @param value The boolean value to store.
   */
  override suspend fun setBoolean(
    key: Preferences.Key<Boolean>,
    value: Boolean,
  ) {
    dataStore.edit { preferences ->
      preferences[key] = value
    }
  }

  /**
   * Observes a boolean value from the key-value store.
   *
   * @param key The key associated with the boolean value.
   * @return A flow with the stored boolean value, or a default value if not found.
   */
  override fun observeBoolean(key: Preferences.Key<Boolean>): Flow<Boolean> =
    dataStore.data.map { preferences ->
      preferences[key] == true
    }

  /**
   * Retrieves a boolean value from the key-value store.
   *
   * @param key The key associated with the boolean value.
   * @return The stored boolean value, or a default value if not found.
   */
  override fun getBoolean(key: Preferences.Key<Boolean>): Boolean =
    runBlocking {
      dataStore.data
        .map { preferences ->
          preferences[key]
        }.first() == true
    }

  /**
   * Removes a boolean value from the key-value store.
   *
   * @param key The key associated with the boolean value to remove.
   */
  override suspend fun removeBoolean(key: Preferences.Key<Boolean>) {
    dataStore.edit { preferences ->
      preferences.remove(key)
    }
  }

  /**
   * Stores a string value in the key-value store.
   *
   * @param key The key associated with the string value.
   * @param value The string value to store.
   */
  override suspend fun setString(
    key: Preferences.Key<String>,
    value: String,
  ) {
    dataStore.edit { preferences ->
      preferences[key] = value
    }
  }

  /**
   * Observes a string value from the key-value store.
   *
   * @param key The key associated with the string value.
   * @return A flow with the stored string value, or null if not found.
   */
  override fun observeString(key: Preferences.Key<String>): Flow<String?> =
    dataStore.data.map { preferences ->
      preferences[key]
    }

  /**
   * Retrieves a string value from the key-value store.
   *
   * @param key The key associated with the string value.
   * @return The stored string value, or null if not found.
   */
  override fun getString(key: Preferences.Key<String>): String? =
    runBlocking {
      dataStore.data
        .map { preferences ->
          preferences[key]
        }.firstOrNull()
    }

  /**
   * Removes a string value from the key-value store.
   *
   * @param key The key associated with the string value to remove.
   */
  override suspend fun removeString(key: Preferences.Key<String>) {
    dataStore.edit { preferences ->
      preferences.remove(key)
    }
  }

  /**
   * Stores a string set value in the key-value store.
   *
   * @param key The key associated with the string set value.
   * @param value The string set value to store.
   */
  override suspend fun setStringSet(
    key: Preferences.Key<Set<String>>,
    value: Set<String>,
  ) {
    dataStore.edit { preferences ->
      preferences[key] = value
    }
  }

  /**
   * Observes a string value from the key-value store.
   *
   * @param key The key associated with the string value.
   * @return A flow with the stored string value, or null if not found.
   */
  override fun observeStringSet(key: Preferences.Key<Set<String>>): Flow<Set<String>?> =
    dataStore.data.map { preferences ->
      preferences[key]
    }

  /**
   * Retrieves a string set value from the key-value store.
   *
   * @param key The key associated with the string set value.
   * @return The stored string set value, or null if not found.
   */
  override fun getStringSet(key: Preferences.Key<Set<String>>): Set<String>? =
    runBlocking {
      dataStore.data
        .map { preferences ->
          preferences[key]
        }.firstOrNull()
    }

  /**
   * Removes a string set value from the key-value store.
   *
   * @param key The key associated with the string set value to remove.
   */
  override suspend fun removeStringSet(key: Preferences.Key<Set<String>>) {
    dataStore.edit { preferences ->
      preferences.remove(key)
    }
  }

  /**
   * Stores a long value in the key-value store.
   *
   * @param key The key associated with the long value.
   * @param value The long value to store.
   */
  override suspend fun setLong(
    key: Preferences.Key<Long>,
    value: Long,
  ) {
    dataStore.edit { preferences ->
      preferences[key] = value
    }
  }

  /**
   * Retrieves a long value from the key-value store.
   *
   * @param key The key associated with the long value.
   * @return The stored long value, or null if not found.
   */
  override fun getLong(key: Preferences.Key<Long>): Long? =
    runBlocking {
      dataStore.data
        .map { preferences ->
          preferences[key]
        }.firstOrNull()
    }

  /**
   * Removes a long value from the key-value store.
   *
   * @param key The key associated with the long value to remove.
   */
  override suspend fun removeLong(key: Preferences.Key<Long>) {
    dataStore.edit { preferences ->
      preferences.remove(key)
    }
  }

  /**
   * Clears all stored key-value pairs in the store.
   */
  override fun clear() {
    runBlocking {
      dataStore.edit {
        it.clear()
      }
    }
  }
}
