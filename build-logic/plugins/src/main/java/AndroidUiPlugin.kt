import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidUiPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configureDependencies()
    }

    private fun Project.configureDependencies() {
        dependencies.apply {
            // Android
            add("implementation", versionCatalog.findLibrary("core.ktx").get())
            add("implementation", versionCatalog.findLibrary("appcompat").get())
            add("implementation", versionCatalog.findLibrary("material").get())

            // Compose
            add("implementation", versionCatalog.findLibrary("compose.material").get())
            add("implementation", versionCatalog.findLibrary("compose.ui.tooling.preview").get())
            add("implementation", versionCatalog.findLibrary("compose.ui.tooling").get())
            add("implementation", versionCatalog.findLibrary("compose.activity").get())
            add("debugImplementation", versionCatalog.findLibrary("compose.ui.tooling").get())
            add("debugImplementation", versionCatalog.findLibrary("compose.ui.test.manifest").get())
            add("androidTestImplementation", versionCatalog.findLibrary("compose.ui.test.junit4").get())
            add("implementation", versionCatalog.findLibrary("dagger.hilt.compose.navigation").get())
        }
    }
}
