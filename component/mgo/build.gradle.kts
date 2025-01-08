plugins {
    id("AndroidComponentPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.component.mgo"
}
dependencies {
    implementation(projects.framework.copy)
    implementation(projects.component.theme)
    implementation(libs.compose.material3)
    implementation(projects.framework.environment)
    testImplementation(testFixtures(projects.framework.environment))
}
