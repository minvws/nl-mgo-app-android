plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.localisation"
    testFixtures {
        enable = true
    }
}

dependencies {
    testFixturesImplementation(libs.kotlin.stdlib)
    testImplementation(testFixtures(projects.framework.test))
    implementation(projects.data.api.load)
    implementation(projects.framework.storage)
    implementation(libs.moshi.sealed)
    ksp(libs.moshi.sealed.codegen)
}
