package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.preferences.core.Preferences

class TestKeyValueStore : KeyValueStore {
    private val strings = HashMap<Preferences.Key<String>, String>(emptyMap())
    private val booleans = HashMap<Preferences.Key<Boolean>, Boolean>(emptyMap())

    override suspend fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    ) {
        booleans[key] = value
    }

    override suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
        return booleans[key] ?: false
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

    override fun clear() {
        strings.clear()
        booleans.clear()
    }
}
