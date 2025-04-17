package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class TestKeyValueStore : KeyValueStore {
  private val strings = MutableStateFlow(HashMap<Preferences.Key<String>, String>(emptyMap()))
  private val booleans = MutableStateFlow(HashMap<Preferences.Key<Boolean>, Boolean>(emptyMap()))
  private val longs = HashMap<Preferences.Key<Long>, Long>(emptyMap())

  override suspend fun setBoolean(
    key: Preferences.Key<Boolean>,
    value: Boolean,
  ) {
    booleans.value[key] = value
  }

  override fun observeBoolean(key: Preferences.Key<Boolean>): Flow<Boolean> {
    return booleans.map { hashMap -> hashMap[key] ?: false }
  }

  override fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
    return booleans.value[key] == true
  }

  override suspend fun removeBoolean(key: Preferences.Key<Boolean>) {
    booleans.value.remove(key)
  }

  override suspend fun setString(
    key: Preferences.Key<String>,
    value: String,
  ) {
    strings.value[key] = value
  }

  override fun observeString(key: Preferences.Key<String>): Flow<String?> {
    return strings.map { hashMap -> hashMap[key] }
  }

  override fun getString(key: Preferences.Key<String>): String? {
    return strings.value[key]
  }

  override suspend fun removeString(key: Preferences.Key<String>) {
    strings.value.remove(key)
  }

  override suspend fun setLong(
    key: Preferences.Key<Long>,
    value: Long,
  ) {
    longs[key] = value
  }

  override fun getLong(key: Preferences.Key<Long>): Long? {
    return longs[key]
  }

  override suspend fun removeLong(key: Preferences.Key<Long>) {
    longs.remove(key)
  }

  override fun clear() {
    strings.value.clear()
    booleans.value.clear()
    longs.clear()
  }
}
