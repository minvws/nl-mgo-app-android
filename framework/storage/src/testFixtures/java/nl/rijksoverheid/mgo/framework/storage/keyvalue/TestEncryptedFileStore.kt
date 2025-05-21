package nl.rijksoverheid.mgo.framework.storage.keyvalue

import nl.rijksoverheid.mgo.framework.storage.file.EncryptedFileStore
import kotlin.reflect.KClass

class TestEncryptedFileStore : EncryptedFileStore {
  private val files = mutableMapOf<String, Any>()

  override suspend fun <O : Any> saveFile(
    value: O,
    clazz: KClass<O>,
    name: String,
  ) {
    files[name] = value
  }

  @Suppress("UNCHECKED_CAST")
  override suspend fun <O : Any> getFile(
    clazz: KClass<O>,
    name: String,
  ): O? {
    return files[name] as O?
  }

  override suspend fun deleteFile(name: String) {
    files.remove(name)
  }

  override suspend fun deleteAll() {
    files.clear()
  }

  fun assertNoFilesSaved(): Boolean {
    return files.isEmpty()
  }
}
