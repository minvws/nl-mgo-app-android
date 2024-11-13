package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.preferences.core.Preferences

class TestKeyValueStore : KeyValueStore {
    private val strings = HashMap<Preferences.Key<String>, String>(emptyMap())
    private val booleans = HashMap<Preferences.Key<Boolean>, Boolean>(emptyMap())
    private val longs = HashMap<Preferences.Key<Long>, Long>(emptyMap())

    override suspend fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    ) {
        booleans[key] = value
    }

    override suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
        return booleans[key] == true
    }

    override suspend fun removeBoolean(key: Preferences.Key<Boolean>) {
        booleans.remove(key)
    }

    override suspend fun setString(
        key: Preferences.Key<String>,
        value: String,
    ) {
        strings[key] = value
    }

    override suspend fun getString(key: Preferences.Key<String>): String? {
        return strings[key]
    }

    override suspend fun removeString(key: Preferences.Key<String>) {
        strings.remove(key)
    }

    override suspend fun setLong(
        key: Preferences.Key<Long>,
        value: Long,
    ) {
        longs[key] = value
    }

    override suspend fun getLong(key: Preferences.Key<Long>): Long? {
        return longs[key]
    }

    override suspend fun removeLong(key: Preferences.Key<Long>) {
        longs.remove(key)
    }

    override fun clear() {
        strings.clear()
        booleans.clear()
    }
}
