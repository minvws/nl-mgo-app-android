package nl.rijksoverheid.mgo.framework.storage.file

import kotlin.reflect.KClass

/**
 * Store that handles encrypted files.
 */
interface EncryptedFileStore {
  /**
   * Save a file securely.
   *
   * @param value The object of which it's contents you want to save.
   * @param clazz The class of the object, so it can serialized to json.
   * @Param name The name of the file, with extension.
   */
  suspend fun <O : Any> saveFile(
    value: O,
    clazz: KClass<O>,
    name: String,
  )

  /**
   * Get a encrypted file.
   *
   * @param clazz The class of the object, so it can be deserialized from json.
   * @Param name The name of the file, with extension.
   */
  suspend fun <O : Any> getFile(
    clazz: KClass<O>,
    name: String,
  ): O?

  /**
   * Delete a encrypted file.
   *
   * @param name The name of the file.
   */
  suspend fun deleteFile(name: String)

  /**
   * Deletes all saved encrypted files.
   */
  suspend fun deleteAll()
}
