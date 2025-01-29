plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.config"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(libs.retrofit.kotlinx.serialization.converter)
    implementation(projects.framework.environment)
    testImplementation(testFixtures(projects.framework.environment))
}
