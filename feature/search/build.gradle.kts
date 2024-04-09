plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.search"
}

dependencies {
    implementation(project(":data:search"))
}
