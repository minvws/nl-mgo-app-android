package nl.rijksoverheid.mgo.framework.storage.keyvalue

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class MemoryMgoKeyValueStorage : MgoKeyValueStorage {
  private val storage = mutableMapOf<KeyValueStorageKey, Any>()
  private val flows = mutableMapOf<KeyValueStorageKey, MutableStateFlow<Any?>>()

  override fun <T : Any> save(
    key: KeyValueStorageKey,
    value: T,
  ) {
    storage[key] = value
    getOrCreateFlow(key).value = value
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> get(key: KeyValueStorageKey): T? = storage[key] as? T

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> observe(key: KeyValueStorageKey): Flow<T?> =
    getOrCreateFlow(key).map { value ->
      value as? T
    }

  override fun delete(key: KeyValueStorageKey) {
    storage.remove(key)
    getOrCreateFlow(key).value = null
  }

  override fun deleteAll() {
    storage.clear()
    flows.values.forEach { it.value = null }
  }

  private fun getOrCreateFlow(key: KeyValueStorageKey): MutableStateFlow<Any?> =
    flows.getOrPut(key) {
      MutableStateFlow(storage[key])
    }
}
