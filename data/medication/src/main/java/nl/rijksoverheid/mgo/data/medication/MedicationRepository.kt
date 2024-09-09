package nl.rijksoverheid.mgo.data.medication

import nl.rijksoverheid.mgo.data.uiSchema.UISchema

interface MedicationRepository {
    suspend fun getMedications(
        organizationId: String,
        resourceEndpoint: String,
    ): Result<List<UISchema>>
}
