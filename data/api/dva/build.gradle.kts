plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.api.dva"
}

dependencies {
    implementation(projects.framework.environment)
    api(libs.fhir.stdu3)
    testImplementation(testFixtures(projects.framework.test))
}
