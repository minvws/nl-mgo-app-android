package nl.rijksoverheid.mgo.data.healthcare.healthCareData

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

interface HealthCareDataRepository {
    suspend fun get(
        organization: MgoOrganization,
        category: HealthCareCategory,
    ): List<Result<List<MgoResourceJson>>>
}
