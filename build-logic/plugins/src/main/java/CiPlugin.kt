import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.json.JSONObject
import java.io.IOException

class CiPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.createRunCiTask()
        project.createValidateCodeCoverageTask()
        project.createPrTask()
    }

    private fun Project.createRunCiTask() {
        tasks.register("runCI") {
            dependsOn("lintTstDebug", "ktlintCheck", project.getVerifySnapshotTasks(), "koverXmlReportTstDebug")
        }
    }

    private fun Project.createValidateCodeCoverageTask() {
        tasks.register("validateCodeCoverage") {
            val sonarToken = System.getenv("SONAR_TOKEN")
            if (sonarToken == null) {
                println("Missing SONAR_TOKEN")
                return@register
            }
            dependsOn("koverXmlReportTstDebug")
            doLast {
                uploadCodeCoverageToSonar()
                validateCodeCoverage()
            }
        }
    }

    private fun Project.createPrTask() {
        tasks.register("createPR") {
            dependsOn("runCI")
            doLast {
                uploadCodeCoverageToSonar()
                val success = validateCodeCoverage()
                if (success) {
                    val openUrl = "https://github.com/minvws/nl-mgo-app-android-private/compare/${project.getCurrentGitBranch()}?expand=1"
                    openBrowser(openUrl)
                }
            }
        }
    }

    private fun Project.validateCodeCoverage(): Boolean {
        // Wait 10 seconds to make sure sonar has the latest code coverage report
        Thread.sleep(10000)

        // Show result
        val codeCoverage = getCodeCoverageFromSonar()
        if (codeCoverage < 80f) {
            println(
                "Code coverage of new code is less than 80%. See: https://sonarcloud" +
                    ".io/project/overview?id=nl-mgo-app-android-private and please fix.",
            )
            return false
        }
        println("Code coverage of new code is ok (>= 80%).")
        return true
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

    private fun Project.getCurrentGitBranch(): String {
        return ProcessBuilder("git", "rev-parse", "--abbrev-ref", "HEAD")
            .directory(rootDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
            .inputStream
            .bufferedReader()
            .readText()
            .trim()
    }

    private fun Project.getCodeCoverageFromSonar(): Float {
        val username = System.getenv("SONAR_TOKEN")
        val password = ""

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://sonarcloud.io/api/measures/component?component=nl-mgo-app-android-private&metricKeys=new_coverage&branch=${getCurrentGitBranch()}")
            .header("Authorization", Credentials.basic(username, password))
            .build()

        val codeCoverage = client.newCall(request).execute().use { response ->
            val json = JSONObject(response.body!!.string())
            val measures = json.getJSONObject("component").getJSONArray("measures")
            if (measures.isEmpty) {
                "100"
            } else {
                measures.getJSONObject(0).getJSONArray("periods").getJSONObject(0)
                    .getString("value")
            }
        }.toFloat()
        println("Code coverage: $codeCoverage")
        return codeCoverage
    }

    private fun openBrowser(url: String) {
        try {
            val os = System.getProperty("os.name").lowercase()
            val command = when {
                os.contains("win") -> listOf("cmd", "/c", "start", url)
                os.contains("mac") -> listOf("open", url)
                os.contains("nix") || os.contains("nux") -> listOf("xdg-open", url)
                else -> throw UnsupportedOperationException("Unsupported OS: $os")
            }
            val processBuilder = ProcessBuilder(command)
            processBuilder.start()
        } catch (e: IOException) {
            println("Failed to open the browser: ${e.message}")
        }
    }
}
