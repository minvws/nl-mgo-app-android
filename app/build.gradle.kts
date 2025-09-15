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
    buildConfigField("String", "BASIC_AUTH_USER", "\"\"")
    buildConfigField("String", "BASIC_AUTH_PASSWORD", "\"\"")
  }

  flavorDimensions += listOf("environment")
  productFlavors {
    // Temporary flavor to demo the app to externals
    create("demo") {
      dimension = "environment"
      applicationIdSuffix = ".demo"
      versionNameSuffix = "-demo"
      manifestPlaceholders["appLabel"] = "@string/app_name_demo"
      manifestPlaceholders["deeplinkHost"] = "mgo-demo"
      buildConfigField("String", "BASIC_AUTH_USER", "\"${System.getenv("BASIC_AUTH_USER")}\"")
      buildConfigField(
        "String",
        "BASIC_AUTH_PASSWORD",
        "\"${System.getenv("BASIC_AUTH_PASSWORD")}\"",
      )
      buildConfigField(
        "String",
        "DEEPLINK_HOST",
        "\"${manifestPlaceholders["deeplinkHost"]}\"",
      )
    }
    create("tst") {
      dimension = "environment"
      applicationIdSuffix = ".tst"
      versionNameSuffix = "-tst"
      manifestPlaceholders["appLabel"] = "@string/app_name_tst"
      manifestPlaceholders["deeplinkHost"] = "mgo-tst"
      buildConfigField("String", "BASIC_AUTH_USER", "\"${System.getenv("BASIC_AUTH_USER")}\"")
      buildConfigField(
        "String",
        "BASIC_AUTH_PASSWORD",
        "\"${System.getenv("BASIC_AUTH_PASSWORD")}\"",
      )
      buildConfigField(
        "String",
        "DEEPLINK_HOST",
        "\"${manifestPlaceholders["deeplinkHost"]}\"",
      )
    }
    create("acc") {
      dimension = "environment"
      applicationIdSuffix = ".acc"
      versionNameSuffix = "-acc"
      manifestPlaceholders["appLabel"] = "@string/app_name_acc"
      manifestPlaceholders["deeplinkHost"] = "mgo-acc"
      buildConfigField("String", "BASIC_AUTH_USER", "\"${System.getenv("BASIC_AUTH_USER")}\"")
      buildConfigField(
        "String",
        "BASIC_AUTH_PASSWORD",
        "\"${System.getenv("BASIC_AUTH_PASSWORD")}\"",
      )
      buildConfigField(
        "String",
        "DEEPLINK_HOST",
        "\"${manifestPlaceholders["deeplinkHost"]}\"",
      )
    }
    create("prod") {
      dimension = "environment"
      manifestPlaceholders["appLabel"] = "@string/app_name"
      manifestPlaceholders["deeplinkHost"] = "mgo"
      buildConfigField(
        "String",
        "DEEPLINK_HOST",
        "\"${manifestPlaceholders["deeplinkHost"]}\"",
      )
    }
  }

  buildFeatures {
    buildConfig = true
  }

  buildTypes {
    debug {
      manifestPlaceholders["allowHttpTraffic"] = true
      manifestPlaceholders["networkSecurityConfig"] = "@xml/network_config_http"
    }
    release {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
      manifestPlaceholders["allowHttpTraffic"] = false
      manifestPlaceholders["networkSecurityConfig"] = "@xml/network_config_https"
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
      productFlavors.getByName("demo").signingConfig = signingConfigs.getByName("release")
    }
  }
}

dependencies {

  // ================================
  // LIBRARIES
  // ================================
  implementation(libs.rootbeer)
  implementation(libs.androidx.startup.runtime)

  // ================================
  // FEATURES
  // ================================

  // Onboarding
  implementation(projects.feature.onboarding.introduction)
  implementation(projects.feature.onboarding.proposition)

  // Pin code
  implementation(projects.feature.pincode.create)
  implementation(projects.feature.pincode.confirm)
  implementation(projects.feature.pincode.login)
  implementation(projects.feature.pincode.biometric)
  implementation(projects.feature.pincode.forgot)
  implementation(projects.feature.pincode.deleted)

  // Dashboard
  implementation(projects.feature.dashboard.bottombar)
  implementation(projects.feature.dashboard.organizations)
  implementation(projects.feature.dashboard.healthCategories)
  implementation(projects.feature.dashboard.editOverview)
  implementation(projects.feature.dashboard.healthCategory)
  implementation(projects.feature.dashboard.uischema)
  implementation(projects.feature.dashboard.removeOrganization)
  implementation(projects.feature.dashboard.settings.home)
  implementation(projects.feature.dashboard.settings.display)
  implementation(projects.feature.dashboard.settings.security)
  implementation(projects.feature.dashboard.settings.advanced)
  implementation(projects.feature.dashboard.settings.about.home)
  implementation(projects.feature.dashboard.settings.about.safety)
  implementation(projects.feature.dashboard.settings.about.opensource)
  implementation(projects.feature.dashboard.settings.about.accessibility)

  // Localisation
  implementation(projects.feature.localisation.addOrganization)
  implementation(projects.feature.localisation.organizationList)

  // DigiD
  implementation(projects.feature.digid)

  // ================================
  // COMPONENTS
  // ================================

  implementation(projects.component.mgo)
  implementation(projects.component.theme)

  // ================================
  // FRAMEWORKS
  // ================================

  implementation(projects.framework.copy)
  implementation(projects.framework.environment)
  implementation(projects.framework.storage)
  implementation(projects.framework.network)
  implementation(projects.framework.featuretoggle)

  // ================================
  // DATA
  // ================================

  implementation(projects.data.onboarding)
  implementation(projects.data.localisation)
  implementation(projects.data.fhirParser)
  implementation(projects.data.healthcare)
  implementation(projects.data.pincode)
  implementation(projects.data.digid)
  implementation(libs.androidx.lifecycle.process)

  // ================================
  // TESTING
  // ================================

  testImplementation(testFixtures(projects.data.onboarding))
  testImplementation(testFixtures(projects.data.pincode))
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.data.digid))
  testImplementation(testFixtures(projects.framework.featuretoggle))
  testImplementation(testFixtures(projects.data.healthcare))
  testImplementation(testFixtures(projects.data.fhirParser))
  testImplementation(libs.mockk.android)

  androidTestImplementation(libs.dagger.hilt.testing)
  androidTestImplementation(libs.junit)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.test.core)
  androidTestImplementation(libs.compose.ui.test.junit4)
  androidTestImplementation(projects.component.pincode)
}
