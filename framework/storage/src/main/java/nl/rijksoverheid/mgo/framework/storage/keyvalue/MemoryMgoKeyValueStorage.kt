package nl.rijksoverheid.mgo.framework.storage.keyvalue

class MemoryMgoKeyValueStorage : MgoKeyValueStorage {
  private val storage = mutableMapOf<KeyValueStorageKey, Any>()

  override fun <T : Any> save(
    key: KeyValueStorageKey,
    value: T,
  ) {
    storage[key] = value
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> get(key: KeyValueStorageKey): T? = storage[key] as? T

  override fun delete(key: KeyValueStorageKey) {
    storage.remove(key)
  }

  override fun deleteAll() {
    storage.clear()
  }
}
