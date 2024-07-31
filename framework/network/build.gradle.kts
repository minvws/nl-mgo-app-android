plugins {
    id("AndroidFrameworkPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(libs.retrofit)
    api(libs.retrofit.moshi)
    api(libs.moshi.kotlin)
    api(libs.okhttp)
    testImplementation(testFixtures(projects.framework.test))
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)
}
