plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.pincode.biometric"
}

dependencies {
    implementation(projects.component.pincode)
    implementation(projects.data.pincode)
    implementation(projects.framework.storage)
    testImplementation(testFixtures(projects.data.pincode))
    testImplementation(testFixtures(projects.framework.storage))
}
