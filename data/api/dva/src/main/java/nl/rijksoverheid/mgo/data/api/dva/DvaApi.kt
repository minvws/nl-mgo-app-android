package nl.rijksoverheid.mgo.data.api.dva

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

// See https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
interface DvaApi {
    @GET("fhir/MedicationStatement")
    suspend fun medicationStatement(
        @Header("x-mgo-dva-target") resourceEndpoint: String,
        @Query("_format") format: String = "json",
        @Query("category") category: String = "urn:oid:2.16.840.1.113883.2.4.3.11.60.20.77.5.3|6",
        @Query("_include") include: String = "MedicationStatement:medication",
    ): ResponseBody

    @GET("fhir/Condition")
    suspend fun condition(
        @Header("x-mgo-dva-target") resourceEndpoint: String,
        @Query("_format") format: String = "json",
    ): ResponseBody

    // Doing it like needs to be done lik this it seems.
    // Retrofit does not seem to like a dollar sign in the path doing it like the other calls :(.
    @GET
    suspend fun observation(
        @Header("x-mgo-dva-target") resourceEndpoint: String,
        @Url url: String,
    ): ResponseBody
}
