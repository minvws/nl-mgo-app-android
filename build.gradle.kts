import org.sonarqube.gradle.SonarProperties

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.firebaseAppdistribution) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.serializable) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.sonarqube)
}

sonar {
    properties {
        property("sonar.organization", "vws")
        property("sonar.projectKey", "nl-mgo-app-android-private")
        property("sonar.projectName", "nl-mgo-app-android-private")
        property("sonar.host.url", "https://sonarcloud.io")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${projectDir}/app/build/reports/kover/reportTstDebug.xml",
        )
        property("sonar.exclusions", getExcludes())
    }
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
