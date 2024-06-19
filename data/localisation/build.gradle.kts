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
    testFixturesImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testFixturesImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation(projects.data.api.load)
    implementation(projects.framework.storage)
}
