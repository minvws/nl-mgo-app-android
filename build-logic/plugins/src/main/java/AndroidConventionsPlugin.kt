import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension

class AndroidConventionsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureKotlin()
        target.configureAndroid()
        target.configureDependencies()
        target.configureKover()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            // Bug in Paparazzi 1.3.5, see: https://github.com/cashapp/paparazzi/issues/1692#issuecomment-2489002011
            CreateSnapshotsDirTask.register(project)

            apply(versionCatalog.findPlugin("kotlinAndroid").get().get().pluginId)
            apply(versionCatalog.findPlugin("ksp").get().get().pluginId)
            apply(versionCatalog.findPlugin("daggerHilt").get().get().pluginId)
            apply(versionCatalog.findPlugin("serializable").get().get().pluginId)
            apply(versionCatalog.findPlugin("kover").get().get().pluginId)
            apply("kotlin-parcelize")
        }
    }

    private fun Project.configureKover() {
        plugins.apply {
            val koverExtension = extensions.getByType<KoverProjectExtension>()
            koverExtension.apply {
                // We use SonarCloud for code analysis on our CI. Unfortunately, SonarCloud currently only supports jacoco xml,
                // so we need to fallback to jacoco reporting.
                useJacoco()
            }
        }
    }

    private fun Project.configureKotlin() {
        plugins.apply {
            val kotlinExtension = extensions.getByType<KotlinAndroidProjectExtension>()
            kotlinExtension.compilerOptions {
                // This is different than setting the jvm in java
                // See https://youtrack.jetbrains.com/issue/KT-66995/JvmTarget-and-JavaVersion-compatibility-for-easier-JVM-version-setup
                jvmTarget.set(JvmTarget.JVM_17)

                // TODO: This warning is suppressed for all modules since Material 3 does have a lot of experimental api's.
                //  By the time this app will go to production it needs to be checked again,
                //  but for development purposes it is suppressed globally
                freeCompilerArgs.set(
                    listOf(
                        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                    ),
                )
            }
        }
    }

    private fun Project.configureAndroid() {
        plugins.apply {
            val minSdkVersion = versionCatalog.findVersion("android.sdk.min").get()
            val targetSdkVersion = versionCatalog.findVersion("android.sdk.target").get()
            val compileSdkVersion = versionCatalog.findVersion("android.sdk.compile").get()
            val androidExtension = extensions.getByType<BaseExtension>()
            androidExtension.apply {
                setCompileSdkVersion(compileSdkVersion.requiredVersion.toInt())
                defaultConfig.apply {
                    minSdk = minSdkVersion.requiredVersion.toInt()
                    setTargetSdkVersion(targetSdkVersion.requiredVersion.toInt())
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                compileOptions.apply {
                    setSourceCompatibility(JAVA_LANGUAGE_VERSION.toString())
                    setTargetCompatibility(JAVA_LANGUAGE_VERSION.toString())
                }
                testOptions.apply {
                    unitTests.apply {
                        isIncludeAndroidResources = true
                    }
                }
                buildTypes.apply {
                    getByName("debug") {
                        enableAndroidTestCoverage = true
                    }
                }
            }
        }
    }

    private fun Project.configureDependencies() {
        dependencies {
            // Add BOMs
            addBillOfMaterials("compose.bom")
            addBillOfMaterials("coroutines.bom")

            // Json serialization
            add("implementation", versionCatalog.findLibrary("kotlinx.serialization.json").get())

            // Coroutines
            add("implementation", versionCatalog.findLibrary("coroutines.core").get())
            add("implementation", versionCatalog.findLibrary("coroutines.android").get())
            add("testImplementation", versionCatalog.findLibrary("coroutines.test").get())

            // Dagger
            add("implementation", versionCatalog.findLibrary("dagger.hilt.android").get())
            add("ksp", versionCatalog.findLibrary("dagger.hilt.compiler").get())

            // Testing
            add("testImplementation", versionCatalog.findLibrary("junit").get())
            add("testImplementation", versionCatalog.findLibrary("turbine").get())
            add("testImplementation", versionCatalog.findLibrary("robolectric").get())
            add("testImplementation", versionCatalog.findLibrary("androidx.test.core").get())
            add("testImplementation", versionCatalog.findLibrary("okhttp.mockwebserver").get())
            add("testImplementation", versionCatalog.findLibrary("mockk.android").get())
            add("testImplementation", testFixtures(project(":framework:test")))

            // Logging
            add("implementation", versionCatalog.findLibrary("timber").get())

            // Test Fixtures
            add("testFixturesImplementation", versionCatalog.findLibrary("coroutines-core").get())
            add("testFixturesImplementation", versionCatalog.findLibrary("coroutines-android").get())

            add("kover", project(project.path))
        }
    }
}
