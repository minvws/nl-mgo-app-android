import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

class AndroidConventionsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureJacoco()
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
                toolVersion = "0.8.11"
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
                // TODO Very ugly :( Needs to be looked at since the app module has flavors and the others do not.
                val task = tasks.findByName("testDebugUnitTest")
                if (task == null) {
                    dependsOn("testTstDebugUnitTest")
                } else {
                    dependsOn("testDebugUnitTest")
                }
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                }
                val buildDir = project.buildDir
                val javaTree = fileTree("$buildDir/intermediates/javac/debug/classes") { setExcludes(fileFilter) }
                val kotlinTree = fileTree("$buildDir/tmp/kotlin-classes/debug") { setExcludes(fileFilter) }
                val execSrc = fileTree(buildDir) { setIncludes(listOf("**/*.exec")) }
                sourceDirectories.setFrom(files("${project.projectDir}/src/main/java"))
                classDirectories.setFrom(files(javaTree, kotlinTree))
                executionData.setFrom(execSrc)
            }
        }
    }

    private val fileFilter by lazy {
        listOf(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "**/androidTest/**",
        )
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
                val kotlinExtension = extensions.getByType<KotlinProjectExtension>()
                kotlinExtension.jvmToolchain(JAVA_LANGUAGE_VERSION.asInt())
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

            // Logging
            add("implementation", versionCatalog.findLibrary("timber").get())
        }
    }
}
