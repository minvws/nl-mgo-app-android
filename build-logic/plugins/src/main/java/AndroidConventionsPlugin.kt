import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.fileTree
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
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
                    excludes = listOf(
                        "jdk.internal.*",
                        "androidx.core.*",
                        "com.android.*",
                        "android.*",
                    )
                }
            }
            tasks.register("runTests", JacocoReport::class.java) {
                description = "Run unit tests, instrumented tests, code coverage and jacoco test report"
                val androidExtension = project.extensions.findByType(AppExtension::class.java)
                val flavorName =
                    androidExtension?.productFlavors?.map { flavor -> flavor.name }?.firstOrNull { flavorName -> flavorName == "tst" }
                        ?.capitalized()
                val sourceName = buildString {
                    if (flavorName == null) {
                        append("debug")
                    } else {
                        append("${flavorName}Debug")
                    }
                }

                // Execute unit tests
                dependsOn("test${sourceName.capitalized()}UnitTest")

                // Execute instrumented test (only if the folder exists to save time)
                val androidTestModules = listOf("storage")
                if (androidTestModules.contains(project.name)) {
                    dependsOn("connected${sourceName.capitalized()}AndroidTest")
                }

                // Generate xml and html code coverage reports
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                }

                // Some default excludes grabbed from a blog post
                val excludes = listOf(
                    "**/R.class",
                    "**/R$*.class",
                    "**/BuildConfig.*",
                    "**/Manifest*.*",
                    "**/*Test*.*",
                    "android/**/*.*",
                    // dagger
                    "**/*_MembersInjector.class",
                    "**/Dagger*Component.class",
                    "**/*Module_*Factory.class",
                    "**/di/module/*",
                    "**/*_Factory*.*",
                    "**/*Module*.*",
                    "**/*Dagger*.*",
                    "**/*Hilt*.*",
                    // kotlin
                    "**/*MapperImpl*.*",
                    "**/BuildConfig.*",
                    "**/*Component*.*",
                    "**/*BR*.*",
                    "**/Manifest*.*",
                    "**/*Companion*.*",
                    "**/*Module*.*",
                    "**/*Dagger*.*",
                    "**/*Hilt*.*",
                    "**/*MembersInjector*.*",
                    "**/*_MembersInjector.class",
                    "**/*_Factory*.*",
                    "**/*_Provide*Factory*.*",
                    "**/*Extensions*.*",
                )

                val buildDir = project.layout.buildDirectory.asFile.get()
                val javaClasses = fileTree("$buildDir/intermediates/javac/${sourceName}/classes") { setExcludes(excludes) }
                val kotlinClasses = fileTree("$buildDir/tmp/kotlin-classes/${sourceName}") { setExcludes(excludes) }
                classDirectories.setFrom(
                    files(
                        listOf(
                            javaClasses,
                            kotlinClasses,
                        ),
                    ),
                )
                sourceDirectories.setFrom(files(listOf("src/main/java")))

                val androidTestData = fileTree("${buildDir}/outputs/code_coverage/${sourceName}AndroidTest/connected/") {
                    setIncludes(listOf("**/*.ec"))
                }

                executionData.setFrom(
                    files(
                        listOf(
                            fileTree(buildDir) { setIncludes(listOf("**/*.exec")) },
                            androidTestData,
                        ),
                    ),
                )
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

            // Moshi
            add("ksp", versionCatalog.findLibrary("moshi.kotlin.codegen").get())

            // Test Fixtures
            add("testFixturesImplementation", versionCatalog.findLibrary("coroutines-core").get())
            add("testFixturesImplementation", versionCatalog.findLibrary("coroutines-android").get())
        }
    }
}
