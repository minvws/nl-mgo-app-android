package nl.rijksoverheid.mgo.data.config

import retrofit2.http.GET

internal interface ConfigApi {
    @GET("/v1/mgo/config")
    suspend fun getConfig(): ConfigResponse
}
