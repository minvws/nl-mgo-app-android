package nl.rijksoverheid.mgo.data.api.dva

import org.hl7.fhir.dstu3.model.Condition
import org.hl7.fhir.dstu3.model.MedicationStatement
import org.hl7.fhir.dstu3.model.Observation
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

// See https://informatiestandaarden.nictiz.nl/wiki/MedMij:V2020.01/FHIR_BGZ_2017
interface DvaApi {

    @Headers("x-mgo-dva-target: https://dva-mock.test.mgo.prolocation.net/48")
    @GET("fhir/MedicationStatement")
    suspend fun medicationStatement(
        @Query("_format") format: String = "json",
        @Query("category") category: String = "urn:oid:2.16.840.1.113883.2.4.3.11.60.20.77.5.3|6",
        @Query("_include") include: String = "MedicationStatement:medication",
    ): List<MedicationStatement>

    @Headers("x-mgo-dva-target: https://dva-mock.test.mgo.prolocation.net/48")
    @GET("fhir/Condition")
    suspend fun condition(
        @Query("_format") format: String = "json",
    ): List<Condition>

    // Doing it like needs to be done lik this it seems.
    // Retrofit does not seem to like a dollar sign in the path doing it like the other calls :(.
    @Headers("x-mgo-dva-target: https://dva-mock.test.mgo.prolocation.net/48")
    @GET
    suspend fun observation(
        @Url url: String,
    ): List<Observation>
}
