plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.healthcareprovider.removeprovider"
}

dependencies {
    implementation(projects.data.localisation)
}
