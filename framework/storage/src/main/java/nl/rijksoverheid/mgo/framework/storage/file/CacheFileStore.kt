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
  fun getFile(name: String): File

  /**
   * Save a file in cache.
   * @param name The name of the file (without extension).
   * @param contentType The contentType, to be used in the [name].
   * @Param base64Content The contents of the file, base64 encoded.
   */
  fun saveFile(
    name: String,
    contentType: String,
    base64Content: String,
  ): File

  /**
   * Delete all files from cache.
   */
  fun deleteAll()
}
