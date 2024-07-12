package nl.rijksoverheid.mgo.data.localisation

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.Flow

interface OrganizationRepository {
    val storedHealthCareProvidersFlow: Flow<List<MgoOrganization>>

    suspend fun search(
        name: String,
        city: String,
    ): Flow<List<MgoOrganization>>

    suspend fun get(): List<MgoOrganization>

    suspend fun save(provider: MgoOrganization)

    suspend fun delete(providerId: String)
}
