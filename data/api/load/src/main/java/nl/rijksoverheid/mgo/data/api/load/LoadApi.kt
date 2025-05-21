package nl.rijksoverheid.mgo.data.api.load

import retrofit2.http.Body
import retrofit2.http.POST

/**
 * API interface that can search for health care providers. (LOAD = Localisatie & Adressering)
 */
interface LoadApi {
  @POST("/localization/organization/search")
  suspend fun search(
    @Body requestBody: SearchRequestBody,
  ): SearchResponse

  @POST("/localization/organization/search-demo")
  suspend fun searchDemo(): SearchResponse
}
