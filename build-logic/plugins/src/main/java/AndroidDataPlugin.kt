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
            // Ideally this would go in :framework:network as a dependency, but ksp is not transitive so it needs to be
            // declared in each module separately.
            add("ksp", versionCatalog.findLibrary("moshi.kotlin.codegen").get())
        }
    }
}
