package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

interface UiSchemaRepository {
    suspend fun getUiSchema(
        organization: MgoOrganization,
        category: HealthCareCategory,
    ): List<Result<List<String>>>
}
