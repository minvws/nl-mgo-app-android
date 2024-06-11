import org.sonarqube.gradle.SonarProperties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.firebaseAppdistribution) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.sonarqube)
}
true // Needed to make the Suppress annotation work for the plugins block

// Dagger and FHIR dependencies both use guava which conflict. Force a single guava version here.
subprojects {
    configurations.all {
        resolutionStrategy {
            eachDependency {
                if (requested.group == "com.google.guava" && requested.name == "guava") {
                    useVersion("33.2.0-jre")
                }
            }
        }
    }
}

sonar {
    properties {
        property("sonar.organization", "vws")
        property("sonar.projectKey", "nl-mgo-app-android-private")
        property("sonar.projectName", "nl-mgo-app-android-private")
        property("sonar.host.url", "https://sonarcloud.io")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${project.projectDir}/**/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml",
        )
        // We exclude all composable screens since it messes with our code coverage
        val exclusions = buildString {
            append("**/res/**/") // Resources folder
            append("**/*Module*.kt") // Dagger modules
            append("**/*Navigation*.kt") // Navigation classes
            append("**/*Screen*.kt") // UI screens
            append("src/main/java/nl/rijksoverheid/mgo/framework/test/**") // Exclude test module
        }
        val composeExclusion = fileTree("../")
            .apply { include("**/*.kt") }
            .filter { file -> file.readText().contains("import androidx.compose.runtime.Composable") }
            .joinToString(",") { file -> "**/${file.name}" }
        property("sonar.exclusions", "$exclusions,$composeExclusion")
    }
}
