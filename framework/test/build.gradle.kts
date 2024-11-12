plugins {
    id("AndroidFrameworkPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.test"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(libs.junit)
    implementation(libs.coroutines.test)
    implementation(libs.okhttp)
    api(libs.okhttp.mockwebserver)
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.core)
    implementation(libs.dagger.hilt.testing)
}
