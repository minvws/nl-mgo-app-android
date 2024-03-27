package nl.rijksoverheid.mgo.framework.storage

import androidx.datastore.preferences.core.Preferences

class TestKeyValueStore : KeyValueStore {
    private val booleans = HashMap<Preferences.Key<Boolean>, Boolean>(emptyMap())

    override fun setBoolean(
        key: Preferences.Key<Boolean>,
        value: Boolean,
    ) {
        booleans[key] = value
    }

    override fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
        return booleans[key] ?: false
    }
}
