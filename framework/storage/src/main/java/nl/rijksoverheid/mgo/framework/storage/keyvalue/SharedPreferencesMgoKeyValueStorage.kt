package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPreferencesMgoKeyValueStorage
  @Inject
  constructor(
    private val sharedPreferences: SharedPreferences,
  ) : MgoKeyValueStorage {
    override fun <T : Any> save(
      key: String,
      value: T,
    ) {
      sharedPreferences.edit {
        when (value) {
          is String -> putString(key, value)
          is Int -> putInt(key, value)
          is Boolean -> putBoolean(key, value)
          is Float -> putFloat(key, value)
          is Long -> putLong(key, value)
          else -> throw IllegalArgumentException("Unsupported type: ${value::class}")
        }
      }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(key: String): T? {
      val allPrefs = sharedPreferences.all
      val value = allPrefs[key] ?: return null

      return when (value) {
        is String, is Int, is Boolean, is Float, is Long -> value as T
        else -> throw IllegalArgumentException("Unsupported type: ${value::class}")
      }
    }

    override fun delete(key: String) {
      sharedPreferences.edit { remove(key) }
    }

    override fun deleteAll() {
      sharedPreferences.edit { clear() }
    }
  }
