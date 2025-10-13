package nl.rijksoverheid.mgo.framework.storage

typealias FileStorageCacheKey = String

interface FileStorage {
  suspend fun save(
    name: FileStorageCacheKey,
    content: ByteArray,
  )

  suspend fun get(name: FileStorageCacheKey): ByteArray?

  suspend fun delete(name: String)
}
