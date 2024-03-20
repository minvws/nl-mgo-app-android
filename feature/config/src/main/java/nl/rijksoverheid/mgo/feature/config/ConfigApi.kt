package nl.rijksoverheid.mgo.feature.config

import retrofit2.http.POST

interface ConfigApi {
    @POST("todos/1")
    suspend fun getConfig(): Config
}
