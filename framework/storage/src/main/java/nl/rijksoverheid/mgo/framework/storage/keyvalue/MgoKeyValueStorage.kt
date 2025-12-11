package nl.rijksoverheid.mgo.framework.storage.keyvalue

import kotlinx.coroutines.flow.Flow

typealias KeyValueStorageKey = String

interface MgoKeyValueStorage {
  fun <T : Any> save(
    key: KeyValueStorageKey,
    value: T,
  )

  fun <T : Any> get(key: KeyValueStorageKey): T?

  fun <T : Any> observe(key: KeyValueStorageKey): Flow<T?>

  fun delete(key: KeyValueStorageKey)

  fun deleteAll()
}
