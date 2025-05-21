import app.cash.paparazzi.gradle.PaparazziPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class SnapshotsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureDependencies()
    }

    private fun Project.configurePlugins() {
        apply<PaparazziPlugin>()
    }

    private fun Project.configureDependencies() {
        dependencies {
            add("testImplementation", testFixtures(project(":framework:snapshots")))
        }
    }
}
