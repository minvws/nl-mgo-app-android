package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import androidx.security.crypto.EncryptedFile
import java.io.File

internal fun createEncryptedFile(
  context: Context,
  masterKeyAlias: String,
  file: File,
): EncryptedFile {
  return EncryptedFile.Builder(
    file,
    context,
    masterKeyAlias,
    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB,
  ).build()
}

internal fun writeEncryptedFile(
  file: EncryptedFile,
  content: ByteArray,
) {
  file.openFileOutput().use { outputStream ->
    outputStream.write(content)
  }
}

internal fun readEncryptedFile(file: EncryptedFile): String {
  return file.openFileInput().use { inputStream ->
    inputStream.readBytes().toString(Charsets.UTF_8)
  }
}
