import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

/**
 * Downloads and extracts the most recent GitHub Actions artifact for a given workflow.
 *
 * @param workflowId The unique identifier of the GitHub Actions workflow whose artifact will be fetched.
 * @param workingDir The target directory where the downloaded ZIP file will be extracted.
 */
fun downloadGithubArtifact(
  workflowId: String,
  workingDir: File,
) {
  val githubToken = System.getenv("MGO_GITHUB_PAT") ?: throw IllegalStateException("Missing env MGO_GITHUB_PAT")

  val client = OkHttpClient()

  // Get workflows
  val workflowsRequest =
    Request
      .Builder()
      .url(
        "https://api.github.com/repos/minvws/nl-mgo-app-web-private/actions/workflows/$workflowId/runs?branch=main&status=success",
      ).addHeader("Authorization", "Bearer $githubToken")
      .addHeader("Accept", "application/vnd.github+json")
      .build()

  val workFlowsResponse = client.newCall(workflowsRequest).execute()
  if (!workFlowsResponse.isSuccessful) {
    throw IllegalStateException("Failed to download Fhir Parser: ${workFlowsResponse.body?.string()}")
  }

  val workflowResponseJson = JSONObject(workFlowsResponse.body!!.string())
  val workflowId = workflowResponseJson.getJSONArray("workflow_runs").getJSONObject(0).getBigInteger("id")

  // Get artifacts
  val artifactsRequest =
    Request
      .Builder()
      .url("https://api.github.com/repos/minvws/nl-mgo-app-web-private/actions/runs/$workflowId/artifacts")
      .addHeader("Authorization", "Bearer $githubToken")
      .addHeader("Accept", "application/vnd.github+json")
      .build()

  val artifactsResponse = client.newCall(artifactsRequest).execute()
  if (!artifactsResponse.isSuccessful) {
    throw IllegalStateException("Failed to download Fhir Parser: ${artifactsResponse.body?.string()}")
  }

  val artifactsResponseJson = JSONObject(artifactsResponse.body!!.string())
  val artifactId = artifactsResponseJson.getJSONArray("artifacts").getJSONObject(0).getBigInteger("id")

  // Get first artifact zip
  val artifactRequest =
    Request
      .Builder()
      .url("https://api.github.com/repos/minvws/nl-mgo-app-web-private/actions/artifacts/$artifactId/zip")
      .addHeader("Authorization", "Bearer $githubToken")
      .addHeader("Accept", "application/vnd.github+json")
      .build()

  val artifactResponse = client.newCall(artifactRequest).execute()
  if (!artifactResponse.isSuccessful) {
    throw IllegalStateException("Failed to download Fhir Parser: ${artifactResponse.body?.string()}")
  }

  // Unzip artifact
  val zipFile = File(workingDir, "artifact.zip")
  artifactResponse.body?.byteStream()?.use { inputStream ->
    FileOutputStream(zipFile).use { outputStream ->
      inputStream.copyTo(outputStream)
    }
  }
  unzip(zipFile, workingDir)
  zipFile.delete()

  // Extract tar
  val tarFile = workingDir.listFiles()?.first { file -> file.extension == "gz" }!!
  extractTarGz(tarFile, workingDir)
  tarFile.delete()
}
