plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.settings"
}

dependencies {
    implementation(projects.framework.featuretoggle)
    implementation(projects.data.localisation)
    implementation(projects.framework.storage)
}
