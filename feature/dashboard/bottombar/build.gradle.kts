plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.bottombar"
}

dependencies {
    implementation(project(":data:localisation"))
}
