package nl.rijksoverheid.mgo.framework.storage.keyvalue

import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import java.io.File

class TestCacheFileStore : CacheFileStore {
  private var savedFiles: Int = 0

  fun assertFileSaved(): Boolean {
    return savedFiles > 0
  }

  fun assertNoFilesSaved(): Boolean {
    return savedFiles == 0
  }

  override fun saveFile(
    name: String,
    contentType: String,
    base64Content: String,
  ): File {
    savedFiles++
    return File("")
  }

  override fun deleteAll() {
    savedFiles = 0
  }
}
