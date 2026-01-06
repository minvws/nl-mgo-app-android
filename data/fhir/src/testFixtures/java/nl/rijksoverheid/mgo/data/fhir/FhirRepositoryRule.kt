package nl.rijksoverheid.mgo.data.fhir

import androidx.test.core.app.ApplicationProvider
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.rules.ExternalResource

class FhirRepositoryRule(
  private val byteArrayStorage: MemoryMgoByteArrayStorage,
) : ExternalResource() {
  private val testServerRule = TestServerRule()

  private lateinit var repository: FhirRepository

  override fun before() {
    super.before()
    testServerRule.before()

    repository =
      DefaultFhirRepository(
        context = ApplicationProvider.getApplicationContext(),
        okHttpClient = OkHttpClient(),
        mgoByteArrayStorage = byteArrayStorage,
        dvaApiBaseUrl = testServerRule.testServer.url(),
      )
  }

  override fun after() {
    super.after()
    testServerRule.after()
  }

  fun getRepository() = repository

  suspend fun enqueueSuccessResponse(
    json: FhirResponseJson,
    organizationId: String,
    endpointId: String,
    fetch: Boolean = true,
  ) {
    val json = readResourceFile(json.file)
    testServerRule.testServer.enqueueJson(json)
    val request =
      FhirRequest(
        organizationId = organizationId,
        medmijId = "1",
        dataServiceId = "48",
        endpointId = endpointId,
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        endpointPath = "",
      )
    if (fetch) {
      repository.fetch(
        request = request,
        forceRefresh = true,
      )
    }
  }

  suspend fun enqueueErrorResponse(
    organizationId: String,
    endpointId: String,
    fetch: Boolean = true,
  ) {
    testServerRule.testServer.enqueue500()
    val request =
      FhirRequest(
        organizationId = organizationId,
        medmijId = "1",
        dataServiceId = "48",
        endpointId = endpointId,
        resourceEndpoint = "",
        fhirVersion = FhirVersion.R3,
        endpointPath = "",
      )
    if (fetch) {
      repository.fetch(
        request = request,
        forceRefresh = true,
      )
    }
  }
}

enum class FhirResponseJson(
  val file: String,
) {
  EMPTY_BUNDLE("emptyBundle.json"),
  ALCOHOL_USE("alcoholUse.json"),
  DRUG_USE("drugUse.json"),
  LIVING_SITUATION("livingSituation.json"),
  NUTRITION_ADVICE("nutritionAdvice.json"),
  TOBACCO_USE("tobaccoUse.json"),
}
