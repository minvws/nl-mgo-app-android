package nl.rijksoverheid.mgo.framework.storage

typealias MgoStorageCacheKey = String

interface MgoStorage {
  suspend fun save(
    name: MgoStorageCacheKey,
    content: ByteArray,
  )

  suspend fun get(name: MgoStorageCacheKey): ByteArray?

  suspend fun delete(name: String)
}
