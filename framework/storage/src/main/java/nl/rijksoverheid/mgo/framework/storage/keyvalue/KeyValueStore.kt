package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

/**
 * An interface for a key-value storage system.
 *
 * This interface provides methods to store, retrieve, and remove
 * key-value pairs of different data types (Boolean, String, Long).
 * It is designed to work with Android's DataStore Preferences API.
 */
interface KeyValueStore {
  /**
   * Stores a boolean value in the key-value store.
   *
   * @param key The key associated with the boolean value.
   * @param value The boolean value to store.
   */
  suspend fun setBoolean(
    key: Preferences.Key<Boolean>,
    value: Boolean,
  )

  /**
   * Observes a boolean value from the key-value store.
   *
   * @param key The key associated with the boolean value.
   * @return A flow with the stored boolean value, or a default value if not found.
   */
  fun observeBoolean(key: Preferences.Key<Boolean>): Flow<Boolean>

  /**
   * Retrieves a boolean value from the key-value store.
   *
   * @param key The key associated with the boolean value.
   * @return The stored boolean value, or a default value if not found.
   */
  fun getBoolean(key: Preferences.Key<Boolean>): Boolean

  /**
   * Removes a boolean value from the key-value store.
   *
   * @param key The key associated with the boolean value to remove.
   */
  suspend fun removeBoolean(key: Preferences.Key<Boolean>)

  /**
   * Stores a string value in the key-value store.
   *
   * @param key The key associated with the string value.
   * @param value The string value to store.
   */
  suspend fun setString(
    key: Preferences.Key<String>,
    value: String,
  )

  /**
   * Observes a string value from the key-value store.
   *
   * @param key The key associated with the string value.
   * @return A flow with the stored string value, or null if not found.
   */
  fun observeString(key: Preferences.Key<String>): Flow<String?>

  /**
   * Retrieves a string value from the key-value store.
   *
   * @param key The key associated with the string value.
   * @return The stored string value, or null if not found.
   */
  fun getString(key: Preferences.Key<String>): String?

  /**
   * Removes a string value from the key-value store.
   *
   * @param key The key associated with the string value to remove.
   */
  suspend fun removeString(key: Preferences.Key<String>)

  /**
   * Stores a long value in the key-value store.
   *
   * @param key The key associated with the long value.
   * @param value The long value to store.
   */
  suspend fun setLong(
    key: Preferences.Key<Long>,
    value: Long,
  )

  /**
   * Retrieves a long value from the key-value store.
   *
   * @param key The key associated with the long value.
   * @return The stored long value, or null if not found.
   */
  fun getLong(key: Preferences.Key<Long>): Long?

  /**
   * Removes a long value from the key-value store.
   *
   * @param key The key associated with the long value to remove.
   */
  suspend fun removeLong(key: Preferences.Key<Long>)

  /**
   * Clears all stored key-value pairs in the store.
   */
  fun clear()
}
