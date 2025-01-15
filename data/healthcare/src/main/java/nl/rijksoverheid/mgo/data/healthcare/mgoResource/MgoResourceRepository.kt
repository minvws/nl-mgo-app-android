package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

interface MgoResourceRepository {
    suspend fun get(
        endpoint: String,
        request: HealthCareRequest,
        organization: MgoOrganization,
    ): Result<List<MgoResource>>

    suspend fun get(referenceId: String): Result<MgoResource>

    suspend fun filter(
        resources: List<MgoResource>,
        profiles: List<String>,
    ): List<MgoResource>
}
