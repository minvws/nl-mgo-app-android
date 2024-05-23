plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.onboarding.privacyoverview"
}

dependencies {
    implementation(project(":data:onboarding"))
}
