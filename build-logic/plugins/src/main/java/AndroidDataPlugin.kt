import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidDataPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureDependencies()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(versionCatalog.findPlugin("androidLibrary").get().get().pluginId)
            apply(AndroidConventionsPlugin::class.java)
            apply(LintPlugin::class.java)
        }
    }

    private fun Project.configureDependencies() {
        dependencies {
            add("implementation", project(":framework:network"))
            add("implementation", testFixtures(project(":framework:test")))
            add("testImplementation", testFixtures(project(":framework:test")))
        }
    }
}
