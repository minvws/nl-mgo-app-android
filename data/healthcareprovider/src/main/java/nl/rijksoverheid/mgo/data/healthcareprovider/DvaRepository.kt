package nl.rijksoverheid.mgo.data.healthcareprovider

import org.hl7.fhir.dstu3.model.MedicationStatement

interface DvaRepository {
    suspend fun getMedicationStatement(): Result<List<MedicationStatement>>
}
