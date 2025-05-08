package nl.rijksoverheid.mgo.data.api.vad

import retrofit2.http.Body
import retrofit2.http.POST

/**
 * API interface that handles authentication with DigiD.
 */
interface VadApi {
  @POST("/oidc/start")
  suspend fun start(
    @Body requestBody: StartRequestBody,
  ): StartResponse
}
