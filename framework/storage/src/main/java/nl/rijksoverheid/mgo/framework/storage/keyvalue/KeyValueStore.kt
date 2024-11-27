package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.preferences.core.Preferences

interface KeyValueStore {
    suspend fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    )

    fun getBoolean(key: Preferences.Key<Boolean>): Boolean

    suspend fun removeBoolean(key: Preferences.Key<Boolean>)

    suspend fun setString(
        key: Preferences.Key<String>,
        value: String,
    )

    fun getString(key: Preferences.Key<String>): String?

    suspend fun removeString(key: Preferences.Key<String>)

    suspend fun setLong(
        key: Preferences.Key<Long>,
        value: Long,
    )

    fun getLong(key: Preferences.Key<Long>): Long?

    suspend fun removeLong(key: Preferences.Key<Long>)

    fun clear()
}
