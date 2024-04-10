package nl.rijksoverheid.mgo.data.localisation.api

import retrofit2.http.Body
import retrofit2.http.GET

internal interface SearchApi {
    @GET("/localization/organization/search")
    suspend fun search(
        @Body requestBody: SearchRequestBody,
    ): SearchResponse
}
