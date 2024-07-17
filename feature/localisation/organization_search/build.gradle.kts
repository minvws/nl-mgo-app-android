plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.localisation.organizationSearch"
}

dependencies {
    implementation(project(":data:localisation"))
    implementation(project(":framework:environment"))
    testImplementation(testFixtures((projects.data.localisation)))
    testImplementation(testFixtures((projects.framework.environment)))
}
