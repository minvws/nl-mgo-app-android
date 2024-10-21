package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.security.crypto.EncryptedSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

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

        override suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
            return encryptedSharedPreferences.getBoolean(key.name, false)
        }

        override suspend fun setString(
            key: Preferences.Key<String>,
            value: String,
        ) {
            encryptedSharedPreferences.edit().putString(key.name, value).apply()
        }

        override suspend fun getString(key: Preferences.Key<String>): String? {
            return encryptedSharedPreferences.getString(key.name, null)
        }
    }
