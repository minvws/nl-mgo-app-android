import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureDependencies()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(versionCatalog.findPlugin("androidLibrary").get().get().pluginId)
            apply(AndroidConventionsPlugin::class.java)
            apply(AndroidUiPlugin::class.java)
            apply(LintPlugin::class.java)

            // Bug in Paparazzi 1.3.5, see: https://github.com/cashapp/paparazzi/issues/1692#issuecomment-2489002011
            CreateSnapshotsDirTask.register(project)
            apply(SnapshotsPlugin::class.java)
        }
    }

    private fun Project.configureDependencies() {
        dependencies {
            add("implementation", project(":component:theme"))
            add("implementation", project(":framework:navigation"))
            add("implementation", project(":framework:copy"))
            add("implementation", project(":framework:test"))
            add("testImplementation", project(":framework:test"))
            add("androidTestImplementation", project(":framework:test"))
        }
    }
}
