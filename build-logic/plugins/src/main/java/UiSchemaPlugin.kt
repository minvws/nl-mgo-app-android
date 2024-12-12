import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.utils.`is`
import java.io.File

/**
 * This plugin downloads shared code that we use for displaying information from FHIR resources.
 * The shared code lives in a javascript file, and it also includes a Types.kt to which we can map the json outputted by the javascript
 * functions.
 * These types are generated via https://quicktype.io/, but the output is not really what we want.
 * This plugin also changes that Types.kt, so that the entire process of updating the shared code is completely automated.
 */
class UiSchemaPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register("updateUiSchema") {

            // TODO Download mgo-fhir-data.life.js
            // TODO Download Types.kt

            val typesFile = File(project.rootDir, "data/uiSchema/src/main/java/nl/rijksoverheid/mgo/data/uiSchema/Types.kt")
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

    /**
     * Quicktype exports to a default package, we change that to our own
     */
    private fun String.updatePackageName(): String {
        return this.replace("package quicktype", "package nl.rijksoverheid.mgo.data.uiSchema")
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
            if (trimmedLine.startsWith("val display: UIEntryDisplay? = null")) {
                updatedLines.add("@Serializable(with = UIEntryDisplaySerializer::class)")
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
