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
        buildConfigField("String", "BASIC_AUTH_USER", "\"\"")
        buildConfigField("String", "BASIC_AUTH_PASSWORD", "\"\"")
    }

    flavorDimensions += listOf("environment")
    productFlavors {
        create("tst") {
            dimension = "environment"
            applicationIdSuffix = ".tst"
            versionNameSuffix = "-tst"
            manifestPlaceholders["appLabel"] = "@string/app_name_tst"
            buildConfigField("String", "BASIC_AUTH_USER", "\"${System.getenv("BASIC_AUTH_USER")}\"")
            buildConfigField("String", "BASIC_AUTH_PASSWORD", "\"${System.getenv("BASIC_AUTH_PASSWORD")}\"")
        }
        create("acc") {
            dimension = "environment"
            applicationIdSuffix = ".acc"
            versionNameSuffix = "-acc"
            manifestPlaceholders["appLabel"] = "@string/app_name_acc"
            buildConfigField("String", "BASIC_AUTH_USER", "\"${System.getenv("BASIC_AUTH_USER")}\"")
            buildConfigField("String", "BASIC_AUTH_PASSWORD", "\"${System.getenv("BASIC_AUTH_PASSWORD")}\"")
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
    implementation(libs.jackson)

    // ================================
    // FEATURES
    // ================================

    // Onboarding
    implementation(projects.feature.onboarding.introduction)
    implementation(projects.feature.onboarding.proposition)

    // Dashboard
    implementation(projects.feature.dashboard.bottombar)
    implementation(projects.feature.dashboard.overview)

    // Localisation
    implementation(projects.feature.localisation.addOrganization)
    implementation(projects.feature.localisation.organizationSearch)
    implementation(projects.feature.localisation.organizationList)

    // Overview
    implementation(projects.feature.organization.removeOrganization)
    implementation(projects.feature.organization.healthCategory)
    implementation(projects.feature.organization.uischemaDetail)

    // Config
    implementation(projects.feature.config)

    // ================================
    // FRAMEWORKS
    // ================================

    implementation(projects.framework.copy)
    implementation(projects.framework.environment)
    implementation(projects.framework.test)
    implementation(projects.framework.storage)
    implementation(projects.framework.network)

    // ================================
    // DATA
    // ================================

    implementation(projects.data.onboarding)
    implementation(projects.data.config)
    implementation(projects.data.localisation)
    implementation(projects.data.uiSchema)
    implementation(projects.data.healthcare)

    // ================================
    // TESTING
    // ================================

    testImplementation(testFixtures(projects.data.onboarding))
    testImplementation(testFixtures(projects.data.config))
    androidTestImplementation(testFixtures(projects.framework.environment))
}
