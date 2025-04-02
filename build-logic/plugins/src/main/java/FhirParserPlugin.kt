import io.kjson.JSON
import io.kjson.pointer.JSONPointer
import net.pwall.json.schema.codegen.CodeGenerator
import net.pwall.util.Name.Companion.capitalise
import okhttp3.OkHttpClient
import okhttp3.Request
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.jetbrains.kotlin.org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.jetbrains.kotlin.org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipInputStream

/**
 * This plugin downloads shared code that we use for displaying information from FHIR resources.
 * The shared code lives in a javascript file, and gives us a json schema from which we can generate kotlin classes.
 * Some manipulation is needed to the json schema for our parser to create the correct modules, which happens after downloading the
 * shared code.
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

            // - Download the latest fhir parser (shared js library and json schema),
            // - Generate kotlin models
            // - Move everything to correct module
            project.downloadFhirParser(githubToken = githubToken)

            // Do some modifications to the generated classes
            project.modifyFhirParserClasses()
        }
    }

    private fun Project.downloadFhirParser(githubToken: String) {
        val jsFile = File(project.rootDir, "data/fhirParser/src/main/assets/mgo-fhir-data.iife.js")

        val workingDir = File(rootDir, "tmp")
        workingDir.mkdir()

        // Get workflows
        val workflowsRequest = Request.Builder()
            .url("https://api.github.com/repos/minvws/nl-mgo-app-web-private/actions/workflows/114414377/runs?status=completed&branch" +
                "=develop")
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
        val versionFile = File(workingDir, "version.json")
        println("Downloaded FHIR Parser. Version: ${versionFile.readText()}")

        // Move version file
        versionFile.renameTo(File(project.rootDir, "data/fhirParser/src/main/assets/mgo-fhir-data.iife.version.json"))

        // Modify types.json
        val downloadedSchemaFile = File(workingDir, "schema/json/types.json")
        val schemaFileJsonObject = JSONObject(downloadedSchemaFile.readText())
        val modifiedSchemaFileJsonObject = modifyJsonSchema(schemaFileJsonObject)

        // Generate kotlin classes based on the json schema
        CodeGenerator().apply {
            baseDirectoryName = File(project.rootDir, "data/fhirParser/src/main/java").path
            configure(File(project.rootDir, "build-logic/plugins/resources/json-schema-config.json"))
            generateAll(JSON.parseNonNull(modifiedSchemaFileJsonObject.toString()), JSONPointer("/definitions"))
        }

        // Clean up
        workingDir.deleteRecursively()
    }

    /**
     * Since the json schema that is generated from the typescript does not fully meets our expectations, we do some
     * modifying so the correct kotlin models are generated.
     */
    private fun modifyJsonSchema(schema: JSONObject): JSONObject {
        // Our parser only parses oneOf, so replace anywhere it finds anyOf with oneOf to work it work
        val modifiedJsonSchema = JSONObject(schema.toString().replace("anyOf", "oneOf"))

        val definitions = modifiedJsonSchema.getJSONObject("definitions")

        // We create our own profiles object so a Profiles class is generate where we can get the profiles from
        val profilesJsonObject = JSONObject().apply {
            put("type", "object")
            put("properties", JSONObject())
        }
        definitions.put("Profiles", profilesJsonObject)

        val definitionsToAdd = mutableListOf<Pair<String, JSONObject>>()

        // Collect keys first to prevent concurrent modification issues
        val keys = definitions.keys().asSequence().toList()

        for (key in keys) {
            val definition = definitions.optJSONObject(key) ?: continue
            val properties = definition.optJSONObject("properties") ?: continue

            for (propertyKey in properties.keys()) {
                val property = properties.optJSONObject(propertyKey) ?: continue
                val type = property.optString("type")

                // The json schema parser we use to generate kotlin models does not handle nested types in "oneOf" without it being
                // defined in the "definitions" json object. This code moves whats inside the items object to a separate object in the
                // "definitions" json object. After, it puts a reference in the items array to that definition. This way the parser
                // knows how to properly create an interface for the child classes that are in oneOf.
                if (type == "array") {
                    val items = property.optJSONObject("items")?.optJSONArray("oneOf") ?: continue
                    val newKeyName = key + propertyKey.capitalise()

                    // Create "oneOf" array efficiently
                    val oneOfArray = JSONArray().apply {
                        for (i in 0 until items.length()) {
                            put(items.getJSONObject(i))
                        }
                    }

                    // Add new definition to schema
                    definitions.put(newKeyName, JSONObject().put("oneOf", oneOfArray))

                    // Store definition in list to ensure safe modification
                    definitionsToAdd.add(newKeyName to JSONObject().put("oneOf", oneOfArray))

                    // Update the "items" reference to new definition
                    properties.getJSONObject(propertyKey).put("items", JSONObject().put("\$ref", "#/definitions/$newKeyName"))
                } else if (propertyKey == "profile") {
                    // For each object we need the profile, which is a string value. Since it's nested inside an class that needs to be
                    // initialised, we want all the profiles inside a class with the values so that we can easily access them. This code
                    // grabs all those profiles and puts them inside a Profiles class.

                    val profileJsonObjectKey = property.getString("const").substringAfterLast("/")
                        .split("-")
                        .joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
                        .replaceFirstChar { it.lowercase() }
                        .replace(".", "")

                    val profileJsonObject = JSONObject().apply {
                        put("type", "string")
                        put("default", property.getString("const"))
                    }

                    profilesJsonObject.getJSONObject("properties").put(profileJsonObjectKey, profileJsonObject)
                }
            }
        }
        return modifiedJsonSchema
    }

    private fun Project.modifyFhirParserClasses() {
        makeInterfacesSealed()
        addSerializeName()
        makeProfilesClassStatic()
    }

    /**
     * Our json schema to kotlin classes code generator, generated interface *classname*. We want it to be:
     *
     * @Serializable
     * sealed interface *classname*.
     *
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

    /**
     * The generated Profiles class is a data class, but it would be nicer if this was a data object.
     * This function does the changes to the Profiles class so that it's converted from a data class to data object.
     */
    private fun Project.makeProfilesClassStatic() {
        val file = File(rootDir, "data/fhirParser/src/main/java/nl/rijksoverheid/mgo/data/fhirParser/models/Profiles.kt")
        var content = file.readText()

        // Remove trailing commas
        content = content.replace(Regex("""(val\s+\w+\s*:\s*\w+\s*=\s*".*?"),\s*\n"""), "$1\n")

        // Replace last ) with }
        content = content.dropLast(2) + " } "

        // Make data class a data object
        content = content.replace("data class Profiles(", "data object Profiles {")

        // Write the modified content back to the file
        file.writeText(content)
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
