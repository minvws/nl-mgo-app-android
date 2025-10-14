package nl.rijksoverheid.mgo.framework.storage.bytearray

import javax.inject.Inject

class MemoryMgoByteArrayStorage
  @Inject
  constructor() : MgoByteArrayStorage {
    private val cache: MutableMap<String, ByteArray> = mutableMapOf()

    override suspend fun save(
      name: MgoStorageCacheKey,
      content: ByteArray,
    ) {
      cache[name] = content
    }

    override suspend fun get(name: MgoStorageCacheKey): ByteArray? = cache[name]

    override suspend fun delete(name: MgoStorageCacheKey) {
      val keys = cache.keys.filter { it.contains(name) }
      for (key in keys) {
        cache.remove(key)
      }
    }
  }
