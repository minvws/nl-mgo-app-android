package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

interface MgoResourceRepository {
    suspend fun get(
        endpoint: String,
        request: HealthCareRequest,
        organization: MgoOrganization,
    ): Result<List<MgoResourceJson>>

    suspend fun get(referenceId: String): Result<MgoResourceJson>

    suspend fun filter(
        resources: List<MgoResourceJson>,
        profiles: List<String>,
    ): List<MgoResourceJson>
}
