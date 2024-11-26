package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import androidx.security.crypto.EncryptedFile
import java.io.File
import kotlin.reflect.KClass
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

internal class EncryptedFileStore(
    val context: Context,
    private val masterKeyAlias: String,
) : FileStore {
    private val json = Json
    private val dir = File(context.filesDir, "encrypted").also { if (!it.exists()) it.mkdir() }

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
        val encryptedFile =
            EncryptedFile.Builder(
                file,
                context,
                masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB,
            ).build()

        // Write json string to file
        val jsonString = json.encodeToString(clazz.serializer(), value)
        encryptedFile.openFileOutput().use { outputStream ->
            outputStream.write(jsonString.toByteArray())
        }
    }

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
        return json.decodeFromString(clazz.serializer(), jsonString)
    }

    override suspend fun deleteFile(name: String) {
        val file = File(dir, name)
        file.delete()
    }
}
