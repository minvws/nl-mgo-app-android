@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("AndroidApplicationPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo"

    defaultConfig {
        applicationId = "nl.rijksoverheid.mgo"
        versionCode = System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull() ?: 999999999
        versionName = "1.0"
    }

    flavorDimensions += listOf("environment")
    productFlavors {
        create("tst") {
            dimension = "environment"
            applicationIdSuffix = ".tst"
            versionNameSuffix = "-tst"
            manifestPlaceholders["appLabel"] = "@string/app_name_tst"
        }
        create("acc") {
            dimension = "environment"
            applicationIdSuffix = ".acc"
            versionNameSuffix = "-acc"
            manifestPlaceholders["appLabel"] = "@string/app_name_acc"
        }
        create("prod") {
            dimension = "environment"
            manifestPlaceholders["appLabel"] = "@string/app_name"
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("../mgo.keystore")
            keyAlias = System.getenv("KEYSTORE_KEY_ALIAS")
            keyPassword = System.getenv("KEYSTORE_PASSWORD")
            storePassword = System.getenv("KEYSTORE_KEY_PASSWORD")
            productFlavors.getByName("tst").signingConfig = signingConfigs.getByName("release")
            productFlavors.getByName("acc").signingConfig = signingConfigs.getByName("release")
            productFlavors.getByName("prod").signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    // Features
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:dashboard"))

    // Frameworks
    implementation(project(":framework:copy"))

    // Data
    implementation(project(":data:onboarding"))
}
