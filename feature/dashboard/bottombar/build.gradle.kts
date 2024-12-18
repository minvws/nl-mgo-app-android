plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.bottombar"
}

dependencies {
    implementation(projects.framework.navigation)
    implementation(libs.kotlin.reflect)
    implementation(projects.data.localisation)
    implementation(projects.data.healthcare)
}
