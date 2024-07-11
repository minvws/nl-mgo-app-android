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
        testInstrumentationRunner = "nl.rijksoverheid.mgo.CustomTestRunner"
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

    // ================================
    // LIBRARIES
    // ================================
    implementation(libs.rootbeer)

    // ================================
    // FEATURES
    // ================================

    // Onboarding
    implementation(projects.feature.onboarding.introduction)
    implementation(projects.feature.onboarding.privacyoverview)

    // Dashboard
    implementation(projects.feature.dashboard.bottombar)
    implementation(projects.feature.dashboard.overview)

    // Localisation
    implementation(projects.feature.localisation.search)
    implementation(projects.feature.localisation.searchresults)
    implementation(projects.feature.localisation.stored)

    // Overview
    implementation(projects.feature.healthcareprovider.details)
    implementation(projects.feature.healthcareprovider.removeprovider)
    implementation(projects.feature.healthcareprovider.medication)
    implementation(projects.feature.healthcareprovider.concern)
    implementation(projects.feature.healthcareprovider.laboratoryTestResult)

    // Config
    implementation(projects.feature.config)

    // ================================
    // FRAMEWORKS
    // ================================

    implementation(projects.framework.copy)
    implementation(projects.framework.environment)
    implementation(projects.framework.test)
    implementation(projects.framework.storage)

    // ================================
    // DATA
    // ================================

    implementation(projects.data.onboarding)
    implementation(projects.data.config)
    implementation(projects.data.localisation)

    // ================================
    // TESTING
    // ================================

    testImplementation(testFixtures(projects.data.onboarding))
    testImplementation(testFixtures(projects.data.config))
}
