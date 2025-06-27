package nl.rijksoverheid.mgo.framework.storage.keyvalue

import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import java.io.File

class TestCacheFileStore : CacheFileStore {
  private var file: File = File("")
  private var savedFiles: Int = 0

  fun setFile(file: File) {
    this.file = file
  }

  fun assertFileSaved(): Boolean = savedFiles > 0

  fun assertNoFilesSaved(): Boolean = savedFiles == 0

  override fun getFile(name: String): File = file

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
