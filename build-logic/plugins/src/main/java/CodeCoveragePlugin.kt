import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.sonarqube.gradle.SonarExtension
import org.sonarqube.gradle.SonarProperties
import org.sonarqube.gradle.SonarQubePlugin

class CodeCoveragePlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.configurePlugin()
        target.configureSonarQube()
    }

    private fun Project.configurePlugin() {
        apply<SonarQubePlugin>()
    }

    private fun Project.configureSonarQube() {
        plugins.apply {
            extensions.configure(SonarExtension::class.java) {
                properties {
                    setupProperties(this)
                }
            }
        }
    }

    private fun Project.setupProperties(properties: SonarProperties) {
        properties.property("sonar.organization", "vws")
        properties.property("sonar.projectKey", "nl-mgo-app-android-private")
        properties.property("sonar.projectName", "nl-mgo-app-android-private")
        properties.property("sonar.host.url", "https://sonarcloud.io")
        properties.property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${projectDir}/app/build/reports/kover/reportTstDebug.xml",
        )
        properties.property("sonar.exclusions", getExcludes())
    }

    private fun Project.getExcludes(): String {
        val pathExclusions = getExcludePaths()
        val contentExclusions = fileTree("../")
            .apply { include("**/*.kt") }
            .filter { file -> getExcludeContents().any { content -> file.readText().contains(content) } }
            .joinToString(",") { file -> "**/${file.name}" }
        return "$pathExclusions,$contentExclusions"
    }

    private fun getExcludePaths(): String = buildList {
        add("**/*Application*.kt") // Application
        add("**/*Activity*.kt") // Activity
        add("**/res/**/") // Resources folder
        add("**/*Module*.kt") // Dagger modules
        add("**/*NavGraph*.kt") // NavGraph classes
        add("app/src/main/java/nl/rijksoverheid/mgo/navigation/**") // Navigation classes
        add("**/DefaultJsRuntimeRepository.kt") // JS Runtime (can be tested with Android Tests)
    }.joinToString(",")

    private fun getExcludeContents(): List<String> = buildList {
        add("import androidx.compose.runtime.Composable") // Exclude all files that contain composables
        add("data class") // Exclude all data classes
        add("sealed class") // Exclude all sealed classes
    }
}
