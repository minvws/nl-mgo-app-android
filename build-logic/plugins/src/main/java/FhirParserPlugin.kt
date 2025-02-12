import io.kjson.JSON
import io.kjson.pointer.JSONPointer
import net.pwall.json.schema.codegen.CodeGenerator
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

            // Download the latest fhir parser, and move the files to the correct modules
            project.downloadFhirParser(githubToken = githubToken)

            project.modifyFhirParserClasses()
        }
    }

    private fun Project.downloadFhirParser(githubToken: String) {
        val jsFile = File(project.rootDir, "data/fhirParser/src/main/assets/mgo-fhir-data.iife.js")

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

        // Create kotlin classes from json schema file, and move them to the correct module
        val downloadedSchemaFile = File(workingDir, "schema/json/types.json")

        // Generate kotlin classes based on the json schema
        CodeGenerator().apply {
            baseDirectoryName = File(project.rootDir, "data/fhirParser/src/main/java").path
            configure(File(project.rootDir, "build-logic/plugins/resources/json-schema-config.json"))
            generateAll(JSON.parseNonNull(downloadedSchemaFile.readText().replace("anyOf", "oneOf")), JSONPointer("/definitions"))
        }

        // Clean up
        workingDir.deleteRecursively()
    }

    private fun Project.modifyFhirParserClasses() {
        makeInterfacesSealed()
        addSerializeName()
    }

    /**
     * Our json schema to kotlin classes code generator, generated interface *classname*. We want it to be:
     * @Serializable
     * sealed interface *classname*.
     * This function loops through all generates kotlin classes, and changes all the interfaces.
     */
    private fun Project.makeInterfacesSealed() {
        val directory = File(rootDir, "data/fhirParser/src/main/java/nl/rijksoverheid/mgo/data/fhirParser/models")
        val interfaceRegex = Regex("""interface (\w+)""") // Matches 'interface ClassName'
        val importStatement = "import kotlinx.serialization.Serializable"
        val packageRegex = Regex("""^package\s+[\w.]+""", RegexOption.MULTILINE)

        directory.walkTopDown()
            .filter { it.extension == "kt" } // Process only Kotlin files
            .forEach { file ->
                val content = file.readText()
                var updatedContent = content
                var shouldAddImport = false

                // Transform interfaces to sealed interfaces with @Serializable
                updatedContent = interfaceRegex.replace(updatedContent) { match ->
                    shouldAddImport = true
                    "@Serializable\nsealed interface ${match.groupValues[1]}"
                }

                // Ensure import is placed two lines below the package statement *only if needed*
                if (shouldAddImport && !updatedContent.contains(importStatement)) {
                    updatedContent = packageRegex.replace(updatedContent) { match ->
                        "${match.value}\n\n$importStatement"
                    }
                }

                if (content != updatedContent) { // Only write if changes were made
                    file.writeText(updatedContent)
                }
            }
    }

    /**
     * Polymorphism is automatically supported by kotlinx serialization if there is a type field present,
     * and if the class is annotated with @SerialName(*type*). This function loops through all generated kotlin classes,
     * and adds that @SerialName. It assumes the type is the same as the class name, but with all capps underscore naming
     * instead of SnakeCase. For example the class name is: DownloadLink; the added annotation will be: @SerialName("DOWNLOAD_LINK").
     */
    private fun Project.addSerializeName() {
        val directory = File(rootDir, "data/fhirParser/src/main/java/nl/rijksoverheid/mgo/data/fhirParser/models")

        val classRegex = Regex("""data class (\w+)\s*\(([^)]*)\)\s*:\s*([\w<>]+)""", RegexOption.DOT_MATCHES_ALL)
        val importStatement = "import kotlinx.serialization.SerialName"
        val suppressAnnotation = "@file:Suppress(\"ktlint\")"
        val packageRegex = Regex("""^package\s+[\w.]+""", RegexOption.MULTILINE)

        directory.walkTopDown()
            .filter { it.extension == "kt" }
            .forEach { file ->
                var content = file.readText()
                var updatedContent = content
                var shouldAddImport = false

                // Ensure @file:Suppress("ktlint") is at the top
                if (!updatedContent.startsWith(suppressAnnotation)) {
                    updatedContent = "$suppressAnnotation\n\n$updatedContent"
                }

                // Modify data classes that contain "val type: String"
                updatedContent = classRegex.replace(updatedContent) { match ->
                    val className = match.groupValues[1]
                    val properties = match.groupValues[2]

                    if (!properties.contains("val type: String")) return@replace match.value

                    shouldAddImport = true // Flag to add the import
                    val serializedName = className.replace(Regex("([a-z])([A-Z])"), "$1_$2").uppercase()
                    """@SerialName("$serializedName")
                ${match.value}"""
                }

                // Ensure the import is placed three lines below the package statement *only if needed*
                if (shouldAddImport && !updatedContent.contains(importStatement)) {
                    updatedContent = packageRegex.replace(updatedContent) { match ->
                        "${match.value}\n\n\n$importStatement"
                    }
                }

                if (content != updatedContent) {
                    file.writeText(updatedContent)
                    println("Updated: ${file.name}")
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
}
