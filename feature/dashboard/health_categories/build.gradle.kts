plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.dashboard.healthCategories"
}

dependencies {
    implementation(libs.moshi.sealed)
    ksp(libs.moshi.sealed.codegen)
    implementation(projects.data.localisation)
    implementation(projects.data.healthcare)
    testImplementation(testFixtures((projects.data.localisation)))
    testImplementation(testFixtures(projects.data.healthcare))
    testImplementation(testFixtures(projects.data.uiSchema))
}
