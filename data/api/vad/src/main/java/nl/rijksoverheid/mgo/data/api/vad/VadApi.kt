package nl.rijksoverheid.mgo.data.api.vad

import retrofit2.http.Body
import retrofit2.http.POST

interface VadApi {
    @POST("/oidc/start")
    suspend fun start(
        @Body requestBody: StartRequestBody,
    ): StartResponse
}
