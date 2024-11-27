package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.security.crypto.EncryptedSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

val KEY_PIN_CODE = stringPreferencesKey("pin_code")

internal class EncryptedSharedPreferencesSecureKeyValueStore
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        masterKeyAlias: String,
    ) : KeyValueStore {
        private val encryptedSharedPreferences =
            EncryptedSharedPreferences.create(
                "app_secure",
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
            )

        override suspend fun setBoolean(
            key: Preferences.Key<Boolean>,
            value: Boolean,
        ) {
            encryptedSharedPreferences.edit().putBoolean(key.name, value).apply()
        }

        override fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
            return encryptedSharedPreferences.getBoolean(key.name, false)
        }

        override suspend fun removeBoolean(key: Preferences.Key<Boolean>) {
            encryptedSharedPreferences.edit().remove(key.name).apply()
        }

        override suspend fun setString(
            key: Preferences.Key<String>,
            value: String,
        ) {
            encryptedSharedPreferences.edit().putString(key.name, value).apply()
        }

        override fun getString(key: Preferences.Key<String>): String? {
            return encryptedSharedPreferences.getString(key.name, null)
        }

        override suspend fun removeString(key: Preferences.Key<String>) {
            encryptedSharedPreferences.edit().remove(key.name).apply()
        }

        override suspend fun setLong(
            key: Preferences.Key<Long>,
            value: Long,
        ) {
            encryptedSharedPreferences.edit().putLong(key.name, value).apply()
        }

        override fun getLong(key: Preferences.Key<Long>): Long? {
            return encryptedSharedPreferences.getLong(key.name, 0L)
        }

        override suspend fun removeLong(key: Preferences.Key<Long>) {
            encryptedSharedPreferences.edit().remove(key.name).apply()
        }

        override fun clear() {
            this.encryptedSharedPreferences.edit().clear().apply()
        }
    }
