package nl.rijksoverheid.mgo.data.config

import retrofit2.http.GET

interface ConfigApi {
    @GET("todos/1")
    suspend fun getConfig(): Config
}
