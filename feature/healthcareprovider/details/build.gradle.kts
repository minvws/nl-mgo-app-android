plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.healthcareprovider.details"
}

dependencies {
    implementation(project(":framework:environment"))
}
