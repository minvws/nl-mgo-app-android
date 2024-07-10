package nl.rijksoverheid.mgo.data.medication.models

import nl.rijksoverheid.mgo.data.medication.MedicationRepository

class TestMedicationRepository(private val result: Result<List<MgoMedication>>) : MedicationRepository {
    override suspend fun getMedications(resourceEndpoint: String): Result<List<MgoMedication>> {
        return result
    }
}
