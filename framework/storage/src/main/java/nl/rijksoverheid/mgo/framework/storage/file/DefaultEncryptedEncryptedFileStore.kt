package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.File
import kotlin.reflect.KClass

/**
 * Store that handles files in app's files directory. Files are encrypted using
 * [EncryptedFile] (https://developer.android.com/reference/androidx/security/crypto/EncryptedFile).
 *
 * @param context The Android application context.
 * @param masterKeyAlias The master key, see [MasterKeys].
 */
internal class DefaultEncryptedEncryptedFileStore(
  val context: Context,
  private val masterKeyAlias: String,
) : EncryptedFileStore {
  private val json = Json
  private val dir = File(context.filesDir, "encrypted").also { if (!it.exists()) check(it.mkdir()) { "Could not create dir" } }

  /**
   * Save a file securely.
   *
   * @param value The object of which it's contents you want to save.
   * @param clazz The class of the object, so it can serialized to json.
   * @Param name The name of the file, with extension.
   */
  @OptIn(InternalSerializationApi::class)
  override suspend fun <O : Any> saveFile(
    value: O,
    clazz: KClass<O>,
    name: String,
  ) {
    // Create file
    val file = File(dir, name)

    // Encrypted file needs to be deleted first if it already exists
    if (file.exists()) {
      check(file.delete()) { "Could not delete file" }
    }

    // Encrypt file
    val encryptedFile = createEncryptedFile(context, masterKeyAlias, file)

    // Write json string to file
    val jsonString = json.encodeToString(clazz.serializer(), value)
    writeEncryptedFile(encryptedFile, jsonString.toByteArray())
  }

  /**
   * Get a encrypted file.
   *
   * @param clazz The class of the object, so it can be deserialized from json.
   * @Param name The name of the file, with extension.
   */
  @OptIn(InternalSerializationApi::class)
  override suspend fun <O : Any> getFile(
    clazz: KClass<O>,
    name: String,
  ): O? {
    // Get file
    val file = File(dir, name)
    if (!file.exists()) {
      return null
    }

    // Get encrypted file
    val encryptedFile = createEncryptedFile(context, masterKeyAlias, file)

    // Decrypt to json string
    val jsonString = readEncryptedFile(encryptedFile)

    // Return object
    return json.decodeFromString(clazz.serializer(), jsonString)
  }

  /**
   * Delete a encrypted file.
   *
   * @param name The name of the file.
   */
  override suspend fun deleteFile(name: String) {
    val file = File(dir, name)
    if (file.exists()) {
      check(file.delete()) { "Could not delete file" }
    }
  }

  override suspend fun deleteAll() {
    val files = dir.listFiles() ?: return
    for (file in files) {
      check(file.delete()) { "Could not delete file" }
    }
  }
}
