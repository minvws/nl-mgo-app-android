plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.bottombar"
}

dependencies {
    implementation(projects.framework.navigation)
    implementation(project(":data:localisation"))
}
