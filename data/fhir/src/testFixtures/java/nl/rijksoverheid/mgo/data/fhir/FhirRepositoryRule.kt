package nl.rijksoverheid.mgo.data.fhir

import androidx.test.core.app.ApplicationProvider
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import okhttp3.OkHttpClient
import org.junit.rules.ExternalResource
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

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
        clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC),
      )
  }

  override fun after() {
    super.after()
    testServerRule.after()
  }

  fun getRepository() = repository

  suspend fun enqueueSuccessResponse(
    request: FhirRequest,
    json: FhirResponseJson,
    fetch: Boolean = true,
  ) {
    val json = readResourceFile(json.file)
    testServerRule.testServer.enqueueJson(json)
    if (fetch) {
      repository.fetch(
        request = request,
        forceRefresh = true,
      )
    }
  }

  suspend fun enqueueErrorResponse(
    request: FhirRequest,
    fetch: Boolean = true,
  ) {
    testServerRule.testServer.enqueue500()
    if (fetch) {
      repository.fetch(
        request = request,
        forceRefresh = true,
      )
    }
  }

  fun enqueueEmptyJson() {
    testServerRule.testServer.enqueueJson("{}")
  }

  fun enqueueIoException() {
    testServerRule.testServer.enqueueIoException()
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
