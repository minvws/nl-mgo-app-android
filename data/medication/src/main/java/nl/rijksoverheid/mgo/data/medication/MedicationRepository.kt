package nl.rijksoverheid.mgo.data.medication

import nl.rijksoverheid.mgo.data.medication.models.MgoMedicationStatement

interface MedicationRepository {
    suspend fun getMedications(): Result<List<MgoMedicationStatement>>
}
