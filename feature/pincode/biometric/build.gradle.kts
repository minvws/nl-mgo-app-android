plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.pincode.biometric"
}

dependencies {
    implementation(projects.component.pincode)
}
