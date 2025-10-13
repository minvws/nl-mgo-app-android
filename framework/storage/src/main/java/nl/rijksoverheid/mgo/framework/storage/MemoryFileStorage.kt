package nl.rijksoverheid.mgo.framework.storage

class MemoryFileStorage : FileStorage {
  private val cache: MutableMap<String, ByteArray> = mutableMapOf()

  override suspend fun save(
    name: FileStorageCacheKey,
    content: ByteArray,
  ) {
    cache[name] = content
  }

  override suspend fun get(name: FileStorageCacheKey): ByteArray? = cache[name]

  override suspend fun delete(name: FileStorageCacheKey) {
    val keys = cache.keys.filter { it.contains(name) }
    for (key in keys) {
      cache.remove(key)
    }
  }
}
