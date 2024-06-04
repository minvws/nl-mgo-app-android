package nl.rijksoverheid.mgo.data.medication

import nl.rijksoverheid.mgo.data.medication.models.MgoMedication

interface MedicationRepository {
    suspend fun getMedications(): Result<List<MgoMedication>>
}
