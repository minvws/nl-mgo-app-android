plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail"
}

dependencies {
    implementation(projects.data.uiSchema)
    implementation(projects.data.healthcare)
    implementation(projects.data.localisation)
}
