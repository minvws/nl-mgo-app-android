plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.config"
}

dependencies {
    implementation(project(":framework:environment"))
}
