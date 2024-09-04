plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.uiSchemaDetail"
}

dependencies {
    implementation(projects.data.uiSchema)
    implementation(testFixtures(projects.data.uiSchema))
}
