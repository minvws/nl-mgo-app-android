plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.healthcareprovider.medication"
}

dependencies {
    implementation(projects.data.medication)
}
