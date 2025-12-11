package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/**
 * Key that is linked to the stored pin code.
 */
val KEY_PIN_CODE = stringPreferencesKey("pin_code")

/**
 * Key-value storage system that saves encrypted data using [SharedPreferences].
 *
 * @param encryptedSharedPreferences The encrypted [SharedPreferences].
 */
internal class EncryptedSharedPreferencesSecureKeyValueStore
  @Inject
  constructor(
    private val encryptedSharedPreferences: SharedPreferences,
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
      encryptedSharedPreferences.edit { putBoolean(key.name, value) }
    }

    /**
     * Observes a boolean value from the key-value store.
     *
     * @param key The key associated with the boolean value.
     * @return A flow with the stored boolean value, or a default value if not found.
     */
    override fun observeBoolean(key: Preferences.Key<Boolean>): Flow<Boolean> =
      callbackFlow {
        val listener =
          SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key.name) {
              trySend(encryptedSharedPreferences.getBoolean(key.name, false))
            }
          }

        encryptedSharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        trySend(encryptedSharedPreferences.getBoolean(key.name, false))

        awaitClose {
          encryptedSharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
      }.distinctUntilChanged()

    /**
     * Retrieves a boolean value from the key-value store.
     *
     * @param key The key associated with the boolean value.
     * @return The stored boolean value, or a default value if not found.
     */
    override fun getBoolean(key: Preferences.Key<Boolean>): Boolean = encryptedSharedPreferences.getBoolean(key.name, false)

    /**
     * Removes a boolean value from the key-value store.
     *
     * @param key The key associated with the boolean value to remove.
     */
    override suspend fun removeBoolean(key: Preferences.Key<Boolean>) {
      encryptedSharedPreferences.edit { remove(key.name) }
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
      encryptedSharedPreferences.edit { putString(key.name, value) }
    }

    /**
     * Observes a boolean value from the key-value store.
     *
     * @param key The key associated with the boolean value.
     * @return A flow with the stored boolean value, or a default value if not found.
     */
    override fun observeString(key: Preferences.Key<String>): Flow<String?> =
      callbackFlow {
        val listener =
          SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key.name) {
              trySend(encryptedSharedPreferences.getString(key.name, null))
            }
          }

        encryptedSharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        trySend(encryptedSharedPreferences.getString(key.name, null))

        awaitClose {
          encryptedSharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
      }.distinctUntilChanged()

    /**
     * Retrieves a string value from the key-value store.
     *
     * @param key The key associated with the string value.
     * @return The stored string value, or null if not found.
     */
    override fun getString(key: Preferences.Key<String>): String? = encryptedSharedPreferences.getString(key.name, null)

    /**
     * Removes a string value from the key-value store.
     *
     * @param key The key associated with the string value to remove.
     */
    override suspend fun removeString(key: Preferences.Key<String>) {
      encryptedSharedPreferences.edit { remove(key.name) }
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
      encryptedSharedPreferences.edit { putStringSet(key.name, value) }
    }

    /**
     * Observes a string value from the key-value store.
     *
     * @param key The key associated with the string value.
     * @return A flow with the stored string value, or null if not found.
     */
    override fun observeStringSet(key: Preferences.Key<Set<String>>): Flow<Set<String>?> =
      callbackFlow {
        val listener =
          SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key.name) {
              trySend(encryptedSharedPreferences.getStringSet(key.name, null))
            }
          }

        encryptedSharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        trySend(encryptedSharedPreferences.getStringSet(key.name, null))

        awaitClose {
          encryptedSharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
      }.distinctUntilChanged()

    /**
     * Retrieves a string set value from the key-value store.
     *
     * @param key The key associated with the string set value.
     * @return The stored string set value, or null if not found.
     */
    override fun getStringSet(key: Preferences.Key<Set<String>>): Set<String>? = encryptedSharedPreferences.getStringSet(key.name, null)

    /**
     * Removes a string set value from the key-value store.
     *
     * @param key The key associated with the string set value to remove.
     */
    override suspend fun removeStringSet(key: Preferences.Key<Set<String>>) {
      encryptedSharedPreferences.edit { remove(key.name) }
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
      encryptedSharedPreferences.edit { putLong(key.name, value) }
    }

    /**
     * Retrieves a long value from the key-value store.
     *
     * @param key The key associated with the long value.
     * @return The stored long value, or null if not found.
     */
    override fun getLong(key: Preferences.Key<Long>): Long? = encryptedSharedPreferences.getLong(key.name, 0L)

    /**
     * Removes a long value from the key-value store.
     *
     * @param key The key associated with the long value to remove.
     */
    override suspend fun removeLong(key: Preferences.Key<Long>) {
      encryptedSharedPreferences.edit { remove(key.name) }
    }

    /**
     * Clears all stored key-value pairs in the store.
     */
    override fun clear() {
      this.encryptedSharedPreferences
        .edit {
          clear()
        }
    }
  }
