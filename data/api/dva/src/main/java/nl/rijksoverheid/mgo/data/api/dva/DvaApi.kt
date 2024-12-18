package nl.rijksoverheid.mgo.data.api.dva

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface DvaApi {
    @GET("fhir/{fhirBinary}")
    suspend fun binary(
        @Header("x-mgo-dva-target") resourceEndpoint: String,
        @Path(value = "fhirBinary", encoded = true) fhirBinary: String,
    ): BinaryResponse

    @GET
    suspend fun get(
        @Header("x-mgo-dva-target") resourceEndpoint: String,
        @Url url: String,
        @QueryMap queries: Map<String, String>
    ): ResponseBody
}
