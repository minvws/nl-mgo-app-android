plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.healthcareprovider.medication"
}

dependencies {
    implementation(projects.data.medication)
    implementation(projects.framework.environment)
    implementation(projects.component.collapsablecard)
}
