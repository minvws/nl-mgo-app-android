package nl.rijksoverheid.mgo.data.healthcareprovider.api

import org.hl7.fhir.dstu3.model.MedicationStatement
import retrofit2.http.GET
import retrofit2.http.Query

internal interface DvaApi {
    @GET("fhir/MedicationStatement")
    suspend fun medicationStatement(
        @Query("_format") format: String = "json",
        @Query("category") category: String = "urn:oid:2.16.840.1.113883.2.4.3.11.60.20.77.5.3|6",
        @Query("_include") include: String = "MedicationStatement:medication",
    ): List<MedicationStatement>
}
