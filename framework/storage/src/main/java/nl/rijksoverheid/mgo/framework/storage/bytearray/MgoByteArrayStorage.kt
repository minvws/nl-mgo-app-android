package nl.rijksoverheid.mgo.framework.storage.bytearray

typealias MgoStorageCacheKey = String

interface MgoByteArrayStorage {
  suspend fun save(
    name: MgoStorageCacheKey,
    content: ByteArray,
  )

  suspend fun get(name: MgoStorageCacheKey): ByteArray?

  suspend fun delete(name: String)
}
