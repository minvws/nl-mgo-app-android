plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.settings.home"
}

dependencies {
    implementation(projects.framework.environment)
}
