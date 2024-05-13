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
    id("org.sonarqube") version "5.0.0.4638"
}
true // Needed to make the Suppress annotation work for the plugins block

sonar {
  properties {
    property("sonar.organization", "vws") 
    property("sonar.projectKey", "nl-mgo-app-android-private")
    property("sonar.host.url", "https://sonarcloud.io")
    property("sonar.coverage.jacoco.xmlReportPaths", "*/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
  }
}
