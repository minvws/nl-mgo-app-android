import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

class AndroidConventionsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureJacoco()
        target.configureKotlin()
        target.configureAndroid()
        target.configureDependencies()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(versionCatalog.findPlugin("kotlinAndroid").get().get().pluginId)
            apply(versionCatalog.findPlugin("ksp").get().get().pluginId)
            apply(versionCatalog.findPlugin("daggerHilt").get().get().pluginId)
            apply("jacoco")
        }
    }

    private fun Project.configureJacoco() {
        plugins.apply {
            val jacocoPluginExtension = extensions.getByType<JacocoPluginExtension>()
            jacocoPluginExtension.apply {
                toolVersion = versionCatalog.findVersion("jacoco-tool").get().requiredVersion.toString()
            }
            tasks.withType(Test::class.java) {
                configure<JacocoTaskExtension> {
                    isIncludeNoLocationClasses = true
                    excludes =
                        listOf(
                            "jdk.internal.*",
                            "androidx.core.*",
                            "com.android.*",
                            "android.*",
                        )
                }
            }
            tasks.register("jacocoTestReport", JacocoReport::class.java) {
                val isAndroidLibrary = project.plugins.hasPlugin("com.android.library")

                // Our app has different flavors and we force the "tst" flavor to run the code coverage on for the main module.
                // All other modules do not have flavors so just use debug.
                val sourceName = if (isAndroidLibrary) "debug" else "tstDebug"

                // Code coverage requires tests to be ran, so depend on that.
                dependsOn("test${sourceName.capitalized()}UnitTest")

                // Required for SonarCloud
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                }

                // Config stuff
                val fileFilters = listOf(
                    "**/R.class",
                    "**/R$*.class",
                    "**/BuildConfig.*",
                    "**/Manifest*.*",
                    "**/*Test*.*",
                    "**/androidTest/**",
                )
                val buildDir = project.layout.buildDirectory.asFile.get()
                val javaTree = fileTree("$buildDir/intermediates/javac/${sourceName}/classes") { setExcludes(fileFilters) }
                val kotlinTree = fileTree("$buildDir/tmp/kotlin-classes/${sourceName}") { setExcludes(fileFilters) }
                val execSrc = fileTree(buildDir) { setIncludes(listOf("**/*.exec")) }
                val coverageSrcDirs = arrayOf("src/main/java")
                sourceDirectories.setFrom(files(coverageSrcDirs))
                additionalSourceDirs.setFrom(files(coverageSrcDirs))
                classDirectories.setFrom(files(javaTree, kotlinTree))
                executionData.setFrom(execSrc)
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
            }
        }
    }

    private fun Project.configureDependencies() {
        dependencies {
            // Add BOMs
            addBillOfMaterials("compose.bom")
            addBillOfMaterials("coroutines.bom")

            // Coroutines
            add("implementation", versionCatalog.findLibrary("coroutines.core").get())
            add("implementation", versionCatalog.findLibrary("coroutines.android").get())
            add("testImplementation", versionCatalog.findLibrary("coroutines.test").get())
            add("androidTestImplementation", versionCatalog.findLibrary("coroutines.test").get())

            // Dagger
            add("implementation", versionCatalog.findLibrary("dagger.hilt.android").get())
            add("ksp", versionCatalog.findLibrary("dagger.hilt.compiler").get())
            add("androidTestImplementation", versionCatalog.findLibrary("dagger.hilt.testing").get())
            add("kspAndroidTest", versionCatalog.findLibrary("dagger.hilt.compiler").get())

            // Testing
            add("testImplementation", versionCatalog.findLibrary("junit").get())
            add("androidTestImplementation", versionCatalog.findLibrary("junit").get())
            add("testImplementation", versionCatalog.findLibrary("turbine").get())
            add("androidTestImplementation", versionCatalog.findLibrary("androidx.test.core").get())
            add("androidTestImplementation", versionCatalog.findLibrary("androidx.junit").get())
            add("androidTestImplementation", versionCatalog.findLibrary("androidx.test.runner").get())
            add("testImplementation", versionCatalog.findLibrary("okhttp.mockwebserver").get())
            add("testImplementation", versionCatalog.findLibrary("mockk.android").get())

            // Logging
            add("implementation", versionCatalog.findLibrary("timber").get())

            // Test Fixtures
            add("testFixturesImplementation", versionCatalog.findLibrary("coroutines-core").get())
            add("testFixturesImplementation", versionCatalog.findLibrary("coroutines-android").get())
        }
    }
}
