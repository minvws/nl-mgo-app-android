plugins {
    id("AndroidFrameworkPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.navigation"
}

dependencies {
    api(libs.compose.navigation)
}
