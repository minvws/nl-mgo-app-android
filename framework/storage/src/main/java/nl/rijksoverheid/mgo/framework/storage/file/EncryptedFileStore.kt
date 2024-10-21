package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import androidx.security.crypto.EncryptedFile
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class EncryptedFileStore
    @Inject
    constructor(
        @ApplicationContext val context: Context,
        @Named("storageMoshi") private val moshi: Moshi,
        private val masterKeyAlias: String,
    ) : FileStore {
        override suspend fun <O : Any> saveFile(
            myObject: O,
            name: String,
        ) {
            withContext(Dispatchers.IO) {
                // Create file
                val file = File(context.cacheDir, name)
                file.delete()

                // Encrypt file
                val encryptedFile =
                    EncryptedFile.Builder(
                        file,
                        context,
                        masterKeyAlias,
                        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB,
                    ).build()

                // Write json string to file
                val json = moshi.adapter<O>(myObject::class.java).toJson(myObject)
                encryptedFile.openFileOutput().use { outputStream ->
                    outputStream.write(json.toByteArray())
                }
            }
        }

        override suspend fun <O : Any> getFile(
            clazz: Class<O>,
            name: String,
        ): O? {
            return withContext(Dispatchers.IO) {
                // Get file
                val file = File(context.cacheDir, name)
                if (!file.exists()) {
                    return@withContext null
                }

                // Get encrypted file
                val encryptedFile =
                    EncryptedFile.Builder(
                        file,
                        context,
                        masterKeyAlias,
                        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB,
                    ).build()

                // Decrypt to json string
                val jsonString =
                    encryptedFile.openFileInput().use { inputStream ->
                        inputStream.readBytes().toString(Charsets.UTF_8)
                    }

                // Return object
                if (jsonString.isEmpty()) {
                    null
                } else {
                    moshi.adapter(clazz).fromJson(jsonString)
                }
            }
        }
    }
