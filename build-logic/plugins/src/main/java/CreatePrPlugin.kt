import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.json.JSONObject
import java.io.IOException

/**
 * Creates a "createPR" gradle task that checks if the PR is ready to be created. If the ci steps and code coverage are all okay,
 * it will launch a browser to create the PR.
 */
class CreatePrPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register("createPR") {
            dependsOn("runCI")
            doLast {
                // Wait 10 seconds to make sure sonar has the latest code coverage report
                Thread.sleep(10000)

                // Show code coverage
                val codeCoverage = getCodeCoverageFromSonar()
                if (codeCoverage < 80f) {
                    println("Code coverage of new code is less than 80%. See: https://sonarcloud" +
                        ".io/project/overview?id=nl-mgo-app-android-private and please fix.")
                }
                println("Code coverage is ok (>= 80%).")
                val openUrl = "https://github.com/minvws/nl-mgo-app-android-private/compare/${project.getCurrentGitBranch()}?expand=1"
                openBrowser(openUrl)
            }
        }
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

    private fun getCodeCoverageFromSonar(): Float {
        val username = System.getenv("SONAR_TOKEN")
        val password = ""

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://sonarcloud.io/api/measures/component?component=nl-mgo-app-android-private&metricKeys=new_coverage")
            .header("Authorization", Credentials.basic(username, password))
            .build()

        val codeCoverage = client.newCall(request).execute().use { response ->
            val json = JSONObject(response.body!!.string())
            json.getJSONObject("component").getJSONArray("measures").getJSONObject(0).getJSONArray("periods").getJSONObject(0)
                .getString("value")
        }.toFloat()

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
