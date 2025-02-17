import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "nl.rijksoverheid.mgo.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

repositories {
    google()
    gradlePluginPortal()
}

dependencies {
    compileOnly(libs.android)
    compileOnly(libs.kotlin)
    compileOnly(libs.ktlint)
    compileOnly(libs.paparazzi.gradle)
    compileOnly(libs.ksp.gradle)
    implementation(libs.okhttp)
    implementation(libs.json)
    implementation(libs.kover)
    implementation(libs.json.kotlin.schema.codegen)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "AndroidApplicationPlugin"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidFeature") {
            id = "AndroidFeaturePlugin"
            implementationClass = "AndroidFeaturePlugin"
        }
        register("androidComponent") {
            id = "AndroidComponentPlugin"
            implementationClass = "AndroidComponentPlugin"
        }
        register("androidFramework") {
            id = "AndroidFrameworkPlugin"
            implementationClass = "AndroidFrameworkPlugin"
        }
        register("androidUi") {
            id = "AndroidUiPlugin"
            implementationClass = "AndroidUiPlugin"
        }
        register("androidData") {
            id = "AndroidDataPlugin"
            implementationClass = "AndroidDataPlugin"
        }
    }
}
