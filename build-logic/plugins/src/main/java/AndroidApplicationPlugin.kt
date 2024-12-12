import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureDependencies()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(versionCatalog.findPlugin("androidApplication").get().get().pluginId)
            apply(versionCatalog.findPlugin("googleServices").get().get().pluginId)
            apply(versionCatalog.findPlugin("firebaseAppdistribution").get().get().pluginId)
            apply(AndroidConventionsPlugin::class.java)
            apply(AndroidUiPlugin::class.java)
            apply(LintPlugin::class.java)
            apply(LokalisePlugin::class.java)
            apply(UiSchemaPlugin::class.java)
        }
    }

    private fun Project.configureDependencies() {
        dependencies {
            add("implementation", project(":component:theme"))
            add("implementation", project(":framework:navigation"))
        }
    }
}
