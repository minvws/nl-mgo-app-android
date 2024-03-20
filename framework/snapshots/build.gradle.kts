plugins {
    id("AndroidFrameworkPlugin")
    id("AndroidUiPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.snapshots"
}

dependencies {
    implementation(libs.paparazzi)
}
