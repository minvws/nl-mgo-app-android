plugins {
    id("AndroidComponentPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.component.theme"
}
dependencies {
    implementation(projects.framework.environment)
    testImplementation(testFixtures(projects.framework.environment))
}
