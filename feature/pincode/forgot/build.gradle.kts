plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.pincode.forgot"
}

dependencies {
    implementation(projects.framework.storage)
    testImplementation(testFixtures(projects.framework.storage))
}
