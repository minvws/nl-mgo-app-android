package nl.rijksoverheid.mgo.data.healthcare.binary

import android.content.Context
import android.util.Base64
import android.util.Base64InputStream
import android.webkit.MimeTypeMap
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

internal class DefaultHealthCareBinaryRepository
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val dvaApi: DvaApi,
    ) : HealthCareBinaryRepository {
        private val attachmentsDir =
            File(context.cacheDir, "attachments").also {
                if (!it.exists()) {
                    check(it.mkdir()) {
                        "Could not create dir"
                    }
                }
            }

        override suspend fun download(
            resourceEndpoint: String,
            fhirBinary: String,
        ): Result<HealthCareBinary> {
            val response =
                executeNetworkRequest {
                    dvaApi.binary(
                        resourceEndpoint = resourceEndpoint,
                        fhirBinary = fhirBinary,
                    )
                }
            return response
                .mapCatching { binaryResponse ->
                    // Get the file extension from the content type
                    val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(binaryResponse.contentType)

                    // Get file name
                    val fileName =
                        buildString {
                            // File name consists of the id
                            append(binaryResponse.id)

                            // And if it exists, the extension
                            if (fileExtension != null) {
                                append(".$fileExtension")
                            }
                        }

                    // Create file in the cache dir
                    val file = File(attachmentsDir, fileName)

                    // Write contents of response to file
                    var outputStream: BufferedOutputStream? = null
                    var inputStream: InputStream? = null

                    try {
                        // Create an InputStream to decode the Base64 string
                        inputStream = ByteArrayInputStream(binaryResponse.content.toByteArray(Charsets.UTF_8))
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

                        HealthCareBinary(file = file, contentType = binaryResponse.contentType)
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
        }

        override suspend fun cleanup() {
            check(attachmentsDir.deleteRecursively()) { "Failed to clean up attachments" }
        }
    }
