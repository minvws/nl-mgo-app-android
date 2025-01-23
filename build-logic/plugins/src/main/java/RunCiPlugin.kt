import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Creates a "runCI" gradle task that runs all the steps that are required for the CI to succeed.
 * The steps are (in order):
 * - Android linting
 * - ktLint
 * - Tests
 * - Generate xml jacoco report
 * - Upload code coverage to sonar
 */
class RunCiPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register("runCI") {
            dependsOn("lintTstDebug", "ktlintCheck", project.getVerifySnapshotTasks(), "koverXmlReportTstDebug")
            doLast {
                project.uploadCodeCoverageToSonar()
            }
        }
    }

    private fun Project.getVerifySnapshotTasks(): List<String> {
        return project.rootProject.subprojects.map { project ->
            project.getTasksByName("verifyPaparazziDebug", false).map { task ->
                task.path
            }
        }.flatten()
    }

    private fun Project.uploadCodeCoverageToSonar() {
        val process = ProcessBuilder()
            .command("./gradlew", "sonar")
            .directory(rootDir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
        process.waitFor()
    }
}
