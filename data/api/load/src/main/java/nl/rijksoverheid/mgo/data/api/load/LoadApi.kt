package nl.rijksoverheid.mgo.data.api.load

import retrofit2.http.Body
import retrofit2.http.POST

interface LoadApi {
    @POST("/localization/organization/search")
    suspend fun search(
        @Body requestBody: SearchRequestBody,
    ): SearchResponse
}
