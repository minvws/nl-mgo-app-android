package nl.rijksoverheid.mgo.data.config.api

import retrofit2.http.GET

internal interface ConfigApi {
    @GET("/v1/mgo/config")
    suspend fun getConfig(): ConfigResponse
}
