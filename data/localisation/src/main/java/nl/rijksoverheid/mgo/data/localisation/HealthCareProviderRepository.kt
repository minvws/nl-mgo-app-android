package nl.rijksoverheid.mgo.data.localisation

import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider

interface HealthCareProviderRepository {
    suspend fun search(
        name: String,
        city: String,
    ): Result<List<HealthCareProvider>>

    suspend fun get(): Result<List<HealthCareProvider>>

    suspend fun save(provider: HealthCareProvider)

    suspend fun delete(provider: HealthCareProvider)
}
