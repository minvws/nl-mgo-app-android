plugins {
    id("AndroidFrameworkPlugin")
    id("AndroidUiPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.navigation"
}

dependencies {
    api(libs.compose.navigation)
    implementation(libs.androidx.browser)
}
