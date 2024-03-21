plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.splash"
}

dependencies {
    implementation(project(":data:config"))
}
