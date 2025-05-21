import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipInputStream

class LokalisePlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register("updateCopy") {
            val projectId = System.getenv("MGO_LOKALISE_PROJECT_ID")
            val apiToken = System.getenv("MGO_LOKALISE_API_TOKEN")
            if (projectId == null) {
                println("Missing MGO_LOKALISE_PROJECT_ID")
                return@register
            }
            if (apiToken == null) {
                println("Missing MGO_LOKALISE_API_TOKEN")
                return@register
            }
            val downloadDir = File(project.rootDir, "framework/copy/src/main/res/")
            if (!downloadDir.exists()) {
                downloadDir.mkdirs()
            }

            println("Downloading copy from Lokalise...")
            val client = OkHttpClient()

            // Create the request to get the download link
            val requestJson = JSONObject().apply {
                put("format", "xml")
                put("bundle_structure", "values-%LANG_ISO%/strings.xml")
                put("replace_breaks", false)
                put("export_empty_as", "base")
                put("export_sort", "first_added")
                put("original_filenames", false)
            }

            val requestBody = requestJson.toString().toRequestBody("application/json".toMediaTypeOrNull())
            val request = Request.Builder()
                .url("https://api.lokalise.com/api2/projects/$projectId/files/download")
                .post(requestBody)
                .addHeader("X-Api-Token", apiToken)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    println("Failed to connect to Lokalise: ${response.body?.string()}")
                    return@register
                }

                val responseJson = JSONObject(response.body!!.string())
                val downloadUrl = responseJson.getString("bundle_url")
                val downloadRequest = Request.Builder().url(downloadUrl).build()
                client.newCall(downloadRequest).execute().use { downloadResponse ->
                    if (!response.isSuccessful) {
                        println("Failed to download copy from Lokalise: ${downloadResponse.body?.string()}")
                    }
                    val zipFile = File(downloadDir, "lokalise_files.zip")
                    downloadResponse.body?.byteStream()?.use { inputStream ->
                        FileOutputStream(zipFile).use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    unzip(zipFile, downloadDir)
                    zipFile.delete()
                    println("Successfully updated copy")
                }
            }
        }
    }

    private fun unzip(zipFile: File, targetDir: File) {
        ZipInputStream(zipFile.inputStream()).use { zipInputStream ->
            var entry = zipInputStream.nextEntry
            while (entry != null) {
                val file = File(targetDir, entry.name)
                if (entry.isDirectory) {
                    file.mkdirs()
                } else {
                    file.parentFile?.mkdirs()
                    FileOutputStream(file).use { outputStream ->
                        zipInputStream.copyTo(outputStream)
                    }
                }
                zipInputStream.closeEntry()
                entry = zipInputStream.nextEntry
            }
        }
    }
}
