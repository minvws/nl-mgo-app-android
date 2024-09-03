plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.organization.medicationUse"
}

dependencies {
    implementation(projects.data.localisation)
    implementation(projects.data.medication)
    implementation(projects.framework.environment)
    implementation(projects.framework.navigation)
    implementation(projects.component.results)
    implementation(projects.data.uiSchema)
    implementation(testFixtures(projects.data.medication))
}
