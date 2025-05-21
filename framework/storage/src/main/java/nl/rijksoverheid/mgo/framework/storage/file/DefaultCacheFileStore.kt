package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import android.util.Base64
import android.util.Base64InputStream
import android.webkit.MimeTypeMap
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Store that handles files in app's cache directory.
 */
internal class DefaultCacheFileStore(context: Context) : CacheFileStore {
  private val cacheDir =
    File(context.cacheDir, "mgo").also {
      if (!it.exists()) {
        check(it.mkdir()) {
          "Could not create dir"
        }
      }
    }

  /**
   * Save a file in cache.
   * @param name The name of the file (without extension).
   * @param contentType The contentType, to be used in the [name].
   * @Param base64Content The contents of the file, base64 encoded.
   */
  override fun saveFile(
    name: String,
    contentType: String,
    base64Content: String,
  ): File {
    // Get the file extension from the content type
    val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentType)

    // Get file name
    val fileName =
      buildString {
        // Append file name
        append(name)

        // And if it exists, the extension
        if (fileExtension != null) {
          append(".$fileExtension")
        }
      }

    // Create file in the cache dir
    val file = File(cacheDir, fileName)

    // Write contents of response to file
    var outputStream: BufferedOutputStream? = null
    var inputStream: InputStream? = null

    try {
      // Create an InputStream to decode the Base64 string
      inputStream = ByteArrayInputStream(base64Content.toByteArray(Charsets.UTF_8))
      val base64InputStream = Base64InputStream(inputStream, Base64.DEFAULT)

      // Create an OutputStream to write to the file
      outputStream = BufferedOutputStream(FileOutputStream(file))

      // Buffer size for writing
      val buffer = ByteArray(4096)
      var bytesRead: Int

      // Read and write in chunks
      while (base64InputStream.read(buffer).also { bytesRead = it } != -1) {
        outputStream.write(buffer, 0, bytesRead)
      }

      return file
    } finally {
      // Close streams to release resources
      try {
        outputStream?.close()
        inputStream?.close()
      } catch (closeException: IOException) {
        closeException.printStackTrace()
      }
    }
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
