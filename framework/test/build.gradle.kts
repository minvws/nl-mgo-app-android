plugins {
    id("AndroidFrameworkPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.test"
}

dependencies {
    implementation(libs.junit)
    implementation(libs.coroutines.test)
}
