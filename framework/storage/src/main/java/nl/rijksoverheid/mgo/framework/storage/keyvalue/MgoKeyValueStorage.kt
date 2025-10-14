package nl.rijksoverheid.mgo.framework.storage.keyvalue

typealias KeyValueStorageKey = String

interface MgoKeyValueStorage {
  fun <T : Any> save(
    key: KeyValueStorageKey,
    value: T,
  )

  fun <T : Any> get(key: KeyValueStorageKey): T?

  fun delete(key: KeyValueStorageKey)

  fun deleteAll()
}
