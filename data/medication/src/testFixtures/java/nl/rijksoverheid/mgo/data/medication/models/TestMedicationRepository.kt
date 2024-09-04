package nl.rijksoverheid.mgo.data.medication.models

import nl.rijksoverheid.mgo.data.medication.MedicationRepository
import nl.rijksoverheid.mgo.data.uiSchema.UISchema

class TestMedicationRepository(private val result: Result<List<UISchema>>) : MedicationRepository {
    override suspend fun getMedications(resourceEndpoint: String): Result<List<UISchema>> {
        return result
    }
}
