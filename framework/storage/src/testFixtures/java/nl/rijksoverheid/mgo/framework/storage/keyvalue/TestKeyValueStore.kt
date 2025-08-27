package nl.rijksoverheid.mgo.framework.storage.keyvalue

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestKeyValueStore : KeyValueStore {
  private val strings = HashMap<Preferences.Key<String>, MutableStateFlow<String?>>()
  private val stringSets = HashMap<Preferences.Key<Set<String>>, MutableStateFlow<Set<String>?>>()
  private val booleans = HashMap<Preferences.Key<Boolean>, MutableStateFlow<Boolean>>()
  private val longs = HashMap<Preferences.Key<Long>, MutableStateFlow<Long?>>()

  override suspend fun setBoolean(
    key: Preferences.Key<Boolean>,
    value: Boolean,
  ) {
    val flow = booleans.getOrPut(key) { MutableStateFlow(false) }
    flow.update { value }
  }

  override fun observeBoolean(key: Preferences.Key<Boolean>): Flow<Boolean> =
    booleans[key] ?: booleans.getOrPut(key) {
      MutableStateFlow(false)
    }

  override fun getBoolean(key: Preferences.Key<Boolean>): Boolean = booleans[key]?.value == true

  override suspend fun removeBoolean(key: Preferences.Key<Boolean>) {
    booleans[key]?.update { false }
  }

  override suspend fun setString(
    key: Preferences.Key<String>,
    value: String,
  ) {
    val flow = strings.getOrPut(key) { MutableStateFlow(null) }
    flow.update { value }
  }

  override fun observeString(key: Preferences.Key<String>): Flow<String?> =
    strings.getOrPut(key) {
      MutableStateFlow(null)
    }

  override fun getString(key: Preferences.Key<String>): String? = strings[key]?.value

  override suspend fun removeString(key: Preferences.Key<String>) {
    strings[key]?.update { null }
  }

  override suspend fun setStringSet(
    key: Preferences.Key<Set<String>>,
    value: Set<String>,
  ) {
    val flow = stringSets.getOrPut(key) { MutableStateFlow(null) }
    flow.update { value }
  }

  override fun observeStringSet(key: Preferences.Key<Set<String>>): Flow<Set<String>?> =
    stringSets.getOrPut(key) {
      MutableStateFlow(emptySet())
    }

  override fun getStringSet(key: Preferences.Key<Set<String>>): Set<String>? = stringSets[key]?.value

  override suspend fun removeStringSet(key: Preferences.Key<Set<String>>) {
    stringSets[key]?.update { null }
  }

  override suspend fun setLong(
    key: Preferences.Key<Long>,
    value: Long,
  ) {
    val flow = longs.getOrPut(key) { MutableStateFlow(null) }
    flow.update { value }
  }

  override fun getLong(key: Preferences.Key<Long>): Long? = longs[key]?.value

  override suspend fun removeLong(key: Preferences.Key<Long>) {
    longs[key]?.update { null }
  }

  override fun clear() {
    strings.map { it.value.update { null } }
    stringSets.map { it.value.update { null } }
    booleans.map { it.value.update { false } }
    longs.map { it.value.update { null } }
    strings.clear()
    stringSets.clear()
    booleans.clear()
    longs.clear()
  }
}
