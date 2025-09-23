package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import android.webkit.MimeTypeMap
import java.io.File

/**
 * Store that handles files in app's cache directory.
 */
internal class DefaultCacheFileStore(
  context: Context,
) : CacheFileStore {
  private val cacheDir =
    File(context.cacheDir, "mgo").also {
      if (!it.exists()) {
        check(it.mkdir()) {
          "Could not create dir"
        }
      }
    }

  override fun getFile(name: String): File = File(cacheDir, name)

  /**
   * Saves a file to the cache directory.
   *
   * @param name The name of the file, without the extension.
   * @param contentType The MIME type of the file, used to determine the file extension.
   * @param content The file contents as a [ByteArray].
   *
   * @return The saved [File] instance.
   */
  override fun saveFile(
    name: String,
    contentType: String,
    content: ByteArray,
  ): File {
    // Get the file extension from the content type
    val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentType)

    // Build the full file name
    val fileName =
      buildString {
        append(name)
        if (fileExtension != null) {
          append(".$fileExtension")
        }
      }

    // Create file in the cache dir
    val file = File(cacheDir, fileName)

    // Write contents directly to the file
    file.outputStream().use { outputStream ->
      outputStream.write(content)
    }

    return file
  }

  /**
   * Delete all files from cache.
   */
  override fun deleteAll() {
    val files = cacheDir.listFiles() ?: return
    for (file in files) {
      check(file.delete()) { "Could not delete file" }
    }
  }
}
