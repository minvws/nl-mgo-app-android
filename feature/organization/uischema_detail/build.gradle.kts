plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.uiSchemaDetail"
}

dependencies {
    implementation(projects.data.uiSchema)
}
