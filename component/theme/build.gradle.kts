plugins {
    id("AndroidComponentPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.component.theme"
}
dependencies {
    implementation(libs.compose.material3)
    implementation(projects.framework.copy)
    implementation(projects.framework.environment)
    testImplementation(testFixtures(projects.framework.environment))
}
