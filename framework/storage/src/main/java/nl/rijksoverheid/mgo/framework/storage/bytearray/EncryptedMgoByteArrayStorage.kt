package nl.rijksoverheid.mgo.framework.storage.bytearray

import android.content.Context
import androidx.security.crypto.EncryptedFile
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class EncryptedMgoByteArrayStorage
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
    @Named("masterKeyAlias") private val masterKeyAlias: String,
  ) : MgoByteArrayStorage {
    override suspend fun save(
      name: MgoStorageCacheKey,
      content: ByteArray,
    ) {
      // Get file
      val file = File(context.filesDir, name)
      file.parentFile?.mkdirs()

      // Get encrypted file
      val encryptedFile =
        EncryptedFile
          .Builder(
            file,
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB,
          ).build()

      // Write contents to encrypted file
      encryptedFile.openFileOutput().use { outputStream ->
        outputStream.write(content)
      }
    }

    override suspend fun get(name: MgoStorageCacheKey): ByteArray? {
      // Get file
      val file = File(context.filesDir, name)
      if (!file.exists()) {
        return null
      }

      // Get encrypted file
      val encryptedFile =
        EncryptedFile
          .Builder(
            file,
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB,
          ).build()

      return encryptedFile.openFileInput().use { inputStream ->
        inputStream.readBytes()
      }
    }

    override suspend fun delete(name: MgoStorageCacheKey) {
      // Get file
      val file = File(context.filesDir, name)

      // Delete
      if (file.exists()) {
        deleteDirectoryRecursively(file)
      }
    }

    private fun deleteDirectoryRecursively(dir: File): Boolean {
      if (!dir.exists()) return true
      if (!dir.isDirectory) return dir.delete()

      dir.listFiles()?.forEach { file ->
        if (file.isDirectory) {
          deleteDirectoryRecursively(file)
        } else {
          file.delete()
        }
      }
      return dir.delete()
    }
  }
