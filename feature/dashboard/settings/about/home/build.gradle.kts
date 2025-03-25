plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.settings.about"
}

dependencies {
    implementation(projects.framework.environment)
    implementation(projects.framework.storage)
    testImplementation(testFixtures(projects.framework.storage))
}
