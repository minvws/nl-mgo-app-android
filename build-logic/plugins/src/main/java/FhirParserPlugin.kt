import io.kjson.JSON
import io.kjson.pointer.JSONPointer
import net.pwall.json.schema.codegen.CodeGenerator
import net.pwall.util.Name.Companion.capitalise
import okhttp3.OkHttpClient
import okhttp3.Request
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class FhirParserPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.tasks.register("updateFhirParser") {
            try {
                // Files are downloaded to this directory
                val workingDir = File(target.rootDir, "tmp")
                workingDir.mkdir()

                // Step 1: Download Files
                downloadFiles(workingDir)

                // Step 2: Move Files
                target.moveFiles(workingDir)

                // Step 3: Modify types.json
                val modifiedTypes = modifyJsonSchema(workingDir)

                // Step 4: Generate Kotlin classes
                target.generateKotlinClasses(modifiedTypes)

                // Step 5: Modify generated classes
                target.modifyGeneratedClasses()

                // Cleanup
                workingDir.deleteRecursively()

            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun downloadFiles(workingDir: File) {
        val githubToken = System.getenv("MGO_GITHUB_PAT") ?: throw IllegalStateException("Missing env MGO_GITHUB_PAT")

        val client = OkHttpClient()

        // Get workflows
        val workflowsRequest = Request.Builder()
            .url("https://api.github.com/repos/minvws/nl-mgo-app-web-private/actions/workflows/114414377/runs?status=completed&branch" +
                "=main")
            .addHeader("Authorization", "Bearer $githubToken")
            .addHeader("Accept", "application/vnd.github+json")
            .build()

        val workFlowsResponse = client.newCall(workflowsRequest).execute()
        if (!workFlowsResponse.isSuccessful) {
            throw IllegalStateException("Failed to download Fhir Parser: ${workFlowsResponse.body?.string()}")
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
            throw IllegalStateException("Failed to download Fhir Parser: ${artifactsResponse.body?.string()}")
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
            throw IllegalStateException("Failed to download Fhir Parser: ${artifactResponse.body?.string()}")
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
    }

    private fun Project.moveFiles(workingDir: File) {
        // Move version.js to correct location
        val targetVersionFile = File(workingDir, "version.json")
        println("Downloaded FHIR Parser. Version: ${targetVersionFile.readText()}")
        val destinationVersionFile = File(project.rootDir, "data/fhirParser/src/main/assets/mgo-fhir-data.iife.version.json")
        targetVersionFile.renameTo(destinationVersionFile)

        // Move mgo-fhir-data.iife.js to correct location
        val targetJsFile = File(workingDir, "js/mgo-fhir-data.iife.js")
        val destinationJsFile = File(project.rootDir, "data/fhirParser/src/main/assets/mgo-fhir-data.iife.js")
        targetJsFile.renameTo(destinationJsFile)
    }

    private fun modifyJsonSchema(workingDir: File): JSONObject {
        val typesFile = File(workingDir, "schema/json/types.json")
        val jsonSchema = JSONObject(typesFile.readText())

        val modifiedJsonSchema = JSONObject(jsonSchema.toString().replace("anyOf", "oneOf"))
        val definitions = modifiedJsonSchema.getJSONObject("definitions")

        val profilesJsonObject = JSONObject().apply {
            put("type", "object")
            put("properties", JSONObject())
        }
        definitions.put("Profiles", profilesJsonObject)

        val keys = definitions.keys().asSequence().toList()

        for (key in keys) {
            val definition = definitions.optJSONObject(key) ?: continue
            val properties = definition.optJSONObject("properties") ?: continue
            for (propertyKey in properties.keys()) {
                val property = properties.optJSONObject(propertyKey) ?: continue
                val type = property.optString("type")

                if (type == "array") {
                    val items = property.optJSONObject("items")?.optJSONArray("oneOf") ?: continue
                    val newKeyName = key + propertyKey.capitalise()

                    val oneOfArray = JSONArray().apply {
                        for (i in 0 until items.length()) {
                            put(items.getJSONObject(i))
                        }
                    }
                    definitions.put(newKeyName, JSONObject().put("oneOf", oneOfArray))
                    properties.getJSONObject(propertyKey).put("items", JSONObject().put("\$ref", "#/definitions/$newKeyName"))
                }

                if (propertyKey == "profile") {
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

                if (property.optString("const").isNotEmpty()) {
                    val constValue = property.getString("const")
                    properties.getJSONObject(propertyKey).put("default", constValue)
                }
            }
        }

        // Write the new json schema to a file (for debugging purposes)
        val modifiedTypeFile = File(workingDir, "schema/json/types_modified.json")
        modifiedTypeFile.writeText(modifiedJsonSchema.toString())

        return modifiedJsonSchema
    }

    private fun Project.generateKotlinClasses(jsonSchema: JSONObject) {
        // Delete old generated kotlin classes
        val kotlinClassesDir = File(rootDir, "data/fhirParser/src/main/java/nl/rijksoverheid/mgo/data/fhirParser/models")
        kotlinClassesDir.deleteRecursively()

        // Generate kotlin classes based on the json schema
        CodeGenerator().apply {
            baseDirectoryName = File(project.rootDir, "data/fhirParser/src/main/java").path
            configure(File(project.rootDir, "build-logic/plugins/resources/json-schema-config.json"))
            generateAll(JSON.parseNonNull(jsonSchema.toString()), JSONPointer("/definitions"))
        }
    }

    private fun Project.modifyGeneratedClasses() {
        makeInterfacesSealed()
        addSerializeName()
        makeProfilesClassStatic()
    }

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

    private fun Project.addSerializeName() {
        val directory = File(rootDir, "data/fhirParser/src/main/java/nl/rijksoverheid/mgo/data/fhirParser/models")
        val classRegex = Regex("""data class (\w+)\s*\(([^)]*)\)\s*:\s*([\w<>]+)""", RegexOption.DOT_MATCHES_ALL)
        val importStatement = "import kotlinx.serialization.SerialName"
        val suppressAnnotation = "@file:Suppress(\"ktlint\")"
        val packageRegex = Regex("""^package\s+[\w.]+""", RegexOption.MULTILINE)

        directory.walkTopDown()
            .filter { it.extension == "kt" }
            .forEach { file ->
                val content = file.readText()
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
}
