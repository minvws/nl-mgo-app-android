package nl.rijksoverheid.mgo.data.medication

import nl.rijksoverheid.mgo.data.medication.models.MgoMedication

interface MedicationRepository {
    suspend fun getMedications(resourceEndpoint: String): Result<List<MgoMedication>>
}
