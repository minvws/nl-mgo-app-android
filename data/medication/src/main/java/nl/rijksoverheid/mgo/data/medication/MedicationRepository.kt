package nl.rijksoverheid.mgo.data.medication

import nl.rijksoverheid.mgo.data.uiSchema.UISchema

interface MedicationRepository {
    suspend fun getMedications(resourceEndpoint: String): Result<List<UISchema>>
}
