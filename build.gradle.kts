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
            "${project.projectDir}/app/build/reports/kover/reportTstDebug.xml",
        )
        val exclusions = buildList {
            add("**/*Application*.kt") // Application
            add("**/*Activity*.kt") // Activities
            add("**/res/**/") // Resources folder
            add("**/*Module*.kt") // Dagger modules
            add("**/*NavGraph*.kt") // NavGraph classes
            add("**/*Screen*.kt") // We exclude all composable screens since it messes with our code coverage
            add("**/*Prompt*.kt") // We exclude all prompts
            add("app/src/main/java/nl/rijksoverheid/mgo/navigation/**") // Exclude navigation module
            add("framework/navigation/src/main/java/nl/rijksoverheid/mgo/framework/navigation/**") // Exclude navigation module
            add("framework/test/src/main/java/nl/rijksoverheid/mgo/framework/test/**") // Exclude test module
            add("**/DefaultJsRuntimeRepository.kt")
        }.joinToString(",")
        val excludeContentInFile = listOf(
            "import androidx.compose.runtime.Composable", // Exclude composables
            "data class", // Exclude data classes
            "sealed class" // Exclude sealed classes
        )
        val composeExclusion = fileTree("../")
            .apply { include("**/*.kt") }
            .filter { file -> excludeContentInFile.any { content -> file.readText().contains(content) } }
            .joinToString(",") { file -> "**/${file.name}" }
        property("sonar.exclusions", "$exclusions,$composeExclusion")
    }
}
