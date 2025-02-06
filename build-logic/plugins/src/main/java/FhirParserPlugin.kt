import okhttp3.OkHttpClient
import okhttp3.Request
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.jetbrains.kotlin.org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.jetbrains.kotlin.org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipInputStream

/**
 * This plugin downloads shared code that we use for displaying information from FHIR resources.
 * The shared code lives in a javascript file, and it also includes a Types.kt to which we can map the json outputted by the javascript
 * functions.
 * These types are generated via https://quicktype.io/, but the output is not really what we want.
 * This plugin also changes that Types.kt, so that the entire process of updating the shared code is completely automated.
 */
class FhirParserPlugin : Plugin<Project> {

    private val client = OkHttpClient()

    override fun apply(project: Project) {
        project.tasks.register("updateFhirParser") {
            val githubToken = System.getenv("MGO_GITHUB_PAT")
            if (githubToken == null) {
                println("Missing MGO_GITHUB_PAT")
                return@register
            }

            // Location of the shared js codebase file
            val jsFile = File(project.rootDir, "data/fhirParser/src/main/assets/mgo-fhir-data.iife.js")

            // Location of the exported types
            val typesFile = File(project.rootDir, "data/fhirParser/src/main/java/nl/rijksoverheid/mgo/data/fhirParser/shared/Types.kt")

            // Download the latest fhir parser, and move the files to the correct modules
            project.downloadFhirParser(githubToken = githubToken, jsFile = jsFile, typesFile = typesFile)

            // We apply some modifications to the Types.kt file
            val typesFileText = typesFile.readText()
            val updatedTypesFileText =
                typesFileText.updatePackageName().also { println("Update package name in Types.kt") }
                    .removeTypealias()
                    .also { println("Removed type aliases from Types.kt") }.
                    removeCodeComments()
                    .also { println("Removed code comments from Types.kt") }
                    .updateImports()
                    .also { println("Updated imports in Types.kt") }
                    .removeLineBreaks()
                    .also { println("Removed line breaks from Types.kt") }
                    .addParcelableToClasses()
                    .also { println("Make classes parcelable in Types.kt") }
                    .addSerializers()
                    .also { println("Add serializers in Types.kt") }
                    .addExcludeFromKtlint()
                    .also { println("Add exclude rule for ktLint in Types.kt") }


            typesFile.writeText(updatedTypesFileText)
        }
    }

    private fun Project.downloadFhirParser(githubToken: String, jsFile: File, typesFile: File) {
        val workingDir = File(rootDir, "tmp")
        workingDir.mkdir()

        // Get workflows
        val workflowsRequest = Request.Builder()
            .url("https://api.github.com/repos/minvws/nl-mgo-app-web-private/actions/workflows/114414377/runs?status=completed&branch=main")
            .addHeader("Authorization", "Bearer $githubToken")
            .addHeader("Accept", "application/vnd.github+json")
            .build()

        val workFlowsResponse = client.newCall(workflowsRequest).execute()
        if (!workFlowsResponse.isSuccessful) {
            println("Failed to download Fhir Parser: ${workFlowsResponse.body?.string()}")
            return
        }

        val workflowResponseJson = JSONObject(workFlowsResponse.body!!.string())
        val workflowId = workflowResponseJson.getJSONArray("workflow_runs").getJSONObject(0).getBigInteger("id")

        // Get artifacts
        val artifactsRequest = Request.Builder()
            .url("https://api.github.com/repos/minvws/nl-mgo-app-web-private/actions/runs/${workflowId}/artifacts")
            .addHeader("Authorization", "Bearer $githubToken")
            .addHeader("Accept", "application/vnd.github+json")
            .build()

        val artifactsResponse = client.newCall(artifactsRequest).execute()
        if (!artifactsResponse.isSuccessful) {
            println("Failed to download Fhir Parser: ${artifactsResponse.body?.string()}")
            return
        }

        val artifactsResponseJson = JSONObject(artifactsResponse.body!!.string())
        val artifactId = artifactsResponseJson.getJSONArray("artifacts").getJSONObject(0).getBigInteger("id")

        // Get first artifact zip
        val artifactRequest = Request.Builder()
            .url("https://api.github.com/repos/minvws/nl-mgo-app-web-private/actions/artifacts/${artifactId}/zip")
            .addHeader("Authorization", "Bearer $githubToken")
            .addHeader("Accept", "application/vnd.github+json")
            .build()

        val artifactResponse = client.newCall(artifactRequest).execute()
        if (!artifactResponse.isSuccessful) {
            println("Failed to download Fhir Parser: ${artifactResponse.body?.string()}")
            return
        }

        // Unzip artifact
        val zipFile = File(workingDir, "artifact.zip")
        artifactResponse.body?.byteStream()?.use { inputStream ->
            FileOutputStream(zipFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        unzip(zipFile, workingDir)
        zipFile.delete()

        // Extract tar
        val tarFile = workingDir.listFiles()?.first { file -> file.extension == "gz" }!!
        extractTarGz(tarFile, workingDir)
        tarFile.delete()

        // Move downloaded js to correct module
        val downloadedJsFile = File(workingDir, "js/mgo-fhir-data.iife.js")
        downloadedJsFile.renameTo(jsFile)

        // Move downloaded schema to correct module
        val downloadedSchemaFile = File(workingDir, "schema/kotlin/Types.kt")
        downloadedSchemaFile.renameTo(typesFile)

        // Clean up
        workingDir.deleteRecursively()
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

    @Suppress("DEPRECATION")
    private fun extractTarGz(tarGzFile: File, targetDir: File) {
        GzipCompressorInputStream(FileInputStream(tarGzFile)).use { gis ->
            TarArchiveInputStream(gis).use { tarInput ->
                var entry: TarArchiveEntry? = tarInput.nextTarEntry
                while (entry != null) {
                    val filePath = "$targetDir/${entry.name}"
                    if (entry.isDirectory) {
                        File(filePath).mkdirs()
                    } else {
                        FileOutputStream(filePath).use { fos -> tarInput.copyTo(fos) }
                    }
                    entry = tarInput.nextTarEntry
                }
            }
        }
    }

    /**
     * Quicktype exports to a default package, we change that to our own
     */
    private fun String.updatePackageName(): String {
        return this.replace("package quicktype", "package nl.rijksoverheid.mgo.data.fhirParser.shared")
    }

    /**
     * Add imports that we need for Parcelable support
     */
    private fun String.updateImports(): String {
        val packageRegex = Regex("^\\s*package\\s+[\\w.]+\\s*$", RegexOption.MULTILINE)
        val importsToAdd = """
    
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

""".trimIndent()

        return this.replace(packageRegex) { match ->
            "${match.value}\n$importsToAdd"
        }
    }

    /**
     * Remove all empty lines above the first data class
     */
    private fun String.removeLineBreaks(): String {
        val result = mutableListOf<String>()
        var removeEmptyLines = true

        for (line in lines()) {
            if (line.isNotEmpty() || (line.isEmpty() && !removeEmptyLines)) {
                result.add(line)
            }

            if (line.trim().startsWith("data class ") && removeEmptyLines) {
                removeEmptyLines = false
            }
        }

        return result.joinToString("\n")
    }

    /**
     * Remove unused type aliases added by quicktype
     */
    private fun String.removeTypealias(): String {
        return this.replace(Regex("^\\s*typealias\\s+\\w+\\s*=\\s*.+$", RegexOption.MULTILINE), "")
    }

    /**
     * Remove unsued code comments added by quicktype
     */
    private fun String.removeCodeComments(): String {
        val result = mutableListOf<String>()

        var insideDataClass = false

        for (line in lines()) {
            when {
                // Detect the start of a data class
                line.trim().startsWith("data class ") -> {
                    insideDataClass = true
                    result.add(line) // Keep the line
                }

                // Detect the end of the data class (assumes closing brace is on its own line or followed by comments)
                insideDataClass && line.trim().endsWith("}") -> {
                    insideDataClass = false
                    result.add(line) // Keep the line
                }

                // Remove comments outside data classes
                !insideDataClass && line.trim().startsWith("//") -> {
                    // Skip this line (remove the comment)
                }

                // Add all other lines
                else -> result.add(line)
            }
        }

        return result.joinToString("\n")
    }

    /**
     * Add parcelable support to the classes inside Types.kt
     */
    private fun String.addParcelableToClasses(): String {
        val lines = this.lines().toMutableList()
        val updatedLines = mutableListOf<String>()

        var addParcelable = true

        for (line in lines) {
            val trimmedLine = line.trim()
            val isEnumClass = trimmedLine.startsWith("enum class")
            val isSealedClass = trimmedLine.startsWith("sealed class")
            val isDataClass = trimmedLine.startsWith("data class")
            val isClass = trimmedLine.startsWith("class")
            val isAnnotation = trimmedLine.startsWith("@")
            val isEndOfClass = trimmedLine.startsWith(")")

            if (isAnnotation) {
                updatedLines.add(line)
                continue
            }

            if (isEnumClass) {
                updatedLines.add(line)
                continue
            }

            if (isSealedClass || isDataClass || isClass) {
                addParcelable = true
                updatedLines.add("@Parcelize")
            }

            when {
                isEndOfClass && addParcelable -> {
                    addParcelable = false
                    updatedLines.add("): Parcelable")
                }
                isSealedClass -> {
                    val className = trimmedLine.split(" ")[2]
                    updatedLines.add("sealed class $className : Parcelable {")
                }
                else -> {
                    updatedLines.add(line)
                }
            }
        }

        return updatedLines.joinToString("\n")
    }

    /**
     * Add serializers that are needed to parse to and from json
     */
    private fun String.addSerializers(): String {
        val lines = this.lines().toMutableList()
        val updatedLines = mutableListOf<String>()

        for (line in lines) {
            val trimmedLine = line.trim()
            if (trimmedLine.startsWith("val display: UIElementDisplay? = null")) {
                updatedLines.add("@Serializable(with = UIElementDisplaySerializer::class)")
            }
            updatedLines.add(line)
        }

        return updatedLines.joinToString("\n")
    }

    /**
     * Exclude this Types.kt from ktlint
     */
    private fun String.addExcludeFromKtlint(): String {
        val lines = this.lines().toMutableList()
        lines.add(0, "@file:Suppress(\"ktlint:standard:no-wildcard-imports\", \"ktlint:standard:max-line-length\")\n")
        return lines.joinToString("\n")
    }
}
