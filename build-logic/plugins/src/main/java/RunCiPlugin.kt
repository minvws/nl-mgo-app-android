import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.json.JSONObject
import java.io.IOException

class RunCiPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register("runCI") {
            dependsOn("lintTstDebug", "ktlintCheck", "koverXmlReportTstDebug")
            doLast {
                project.uploadCodeCoverageToSonar()
                // Wait 10 seconds to make sure sonar has the latest code coverage report
                Thread.sleep(10000)
                val codeCoverage = getCodeCoverageFromSonar()
                if (codeCoverage >= 80f) {
                    println("CI checks are all ✓")
//                    val url = "https://github.com"
//                    openBrowser(url)
                } else {
                    println("Code coverage is ☓, code coverage is below 80%: $codeCoverage")
                }
            }
        }
    }

    private fun Project.uploadCodeCoverageToSonar() {
        val process = ProcessBuilder()
            .command("./gradlew", "sonar")
            .directory(project.rootDir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
        process.waitFor()
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
            val os = System.getProperty("os.name").toLowerCase()
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
