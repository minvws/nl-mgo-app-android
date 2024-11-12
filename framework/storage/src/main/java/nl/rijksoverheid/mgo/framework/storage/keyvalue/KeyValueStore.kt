package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.preferences.core.Preferences

interface KeyValueStore {
    suspend fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    )

    suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean

    suspend fun removeBoolean(key: Preferences.Key<Boolean>)

    suspend fun setString(
        key: Preferences.Key<String>,
        value: String,
    )

    suspend fun getString(key: Preferences.Key<String>): String?

    suspend fun removeString(key: Preferences.Key<String>)

    fun clear()
}
