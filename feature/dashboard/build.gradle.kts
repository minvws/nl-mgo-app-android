plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.dashboard"
}

dependencies {
    implementation(project(":data:onboarding"))
}
