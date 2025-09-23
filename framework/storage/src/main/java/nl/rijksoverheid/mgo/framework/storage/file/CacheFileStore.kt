package nl.rijksoverheid.mgo.framework.storage.file

import java.io.File

/**
 * Store that handles files in cache.
 */
interface CacheFileStore {
  /**
   * Get a file from cache.
   * @param name The file of the file (with extension).
   */
  fun getFile(name: String): File?

  /**
   * Saves a file to the cache directory.
   *
   * @param name The name of the file, without the extension.
   * @param contentType The MIME type of the file, used to determine the file extension.
   * @param content The file contents as a [ByteArray].
   *
   * @return The saved [File] instance.
   */
  fun saveFile(
    name: String,
    contentType: String,
    content: ByteArray,
  ): File

  /**
   * Delete all files from cache.
   */
  fun deleteAll()
}
