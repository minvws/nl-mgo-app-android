package nl.rijksoverheid.mgo.data.localisation

import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import kotlinx.coroutines.flow.Flow

interface HealthCareProviderRepository {
    val storedHealthCareProvidersFlow: Flow<List<HealthCareProvider>>

    suspend fun search(
        name: String,
        city: String,
    ): Flow<List<HealthCareProvider>>

    suspend fun get(): List<HealthCareProvider>

    suspend fun save(provider: HealthCareProvider)

    suspend fun delete(providerId: String)
}
