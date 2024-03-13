@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseAppdistribution)
}

android {
    namespace = "nl.rijksoverheid.mgo"
    compileSdk = 34

    defaultConfig {
        applicationId = "nl.rijksoverheid.mgo"
        minSdk = 29
        targetSdk = 34
        versionCode = System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull() ?: 999999999
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
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

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
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
    // Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.compose.activity)
    testImplementation(libs.junit)
}