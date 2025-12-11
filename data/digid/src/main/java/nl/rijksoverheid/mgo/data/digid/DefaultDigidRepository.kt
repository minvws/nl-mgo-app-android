package nl.rijksoverheid.mgo.data.digid

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import nl.nl.rijksoverheid.mgo.framework.network.executeRequest
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Named

internal class DefaultDigidRepository
  @Inject
  constructor(
    private val okHttpClient: OkHttpClient,
    @Named("dvaApiBaseUrl") private val baseUrl: String,
    private val environmentRepository: EnvironmentRepository,
  ) : DigidRepository {
    private val json = Json.Default

    override suspend fun login(): Result<String> {
      val environment = environmentRepository.getEnvironment()

      val requestBodyJson =
        buildJsonObject {
          put("client_callback_url", "${environment.deeplinkHost}://app/login")
        }
      val requestBodyString = json.encodeToString(requestBodyJson)

      val request =
        Request
          .Builder()
          .url("$baseUrl/oidc/start")
          .post(requestBodyString.toRequestBody("application/json".toMediaType()))
          .build()

      return okHttpClient.executeRequest(request).map { response ->
        val jsonString = response.body?.string() ?: throw IllegalStateException("Response is empty")
        val json = Json.parseToJsonElement(jsonString)
        json.jsonObject["authz_url"]?.jsonPrimitive?.content ?: throw IllegalStateException("client_callback_url is empty")
      }
    }
  }
