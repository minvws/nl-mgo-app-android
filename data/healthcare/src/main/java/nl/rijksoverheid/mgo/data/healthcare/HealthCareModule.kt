package nl.rijksoverheid.mgo.data.healthcare

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HealthCareModule {
    @Provides
    @Singleton
    fun provideHealthCareRepository(
        uiSchemaMapper: UiSchemaMapper,
        dvaApi: DvaApi,
        @Named("dvaApiBaseUrl") dvaApiBaseUrl: String,
    ): HealthCareRepository {
        return DefaultHealthCareRepository(
            uiSchemaMapper = uiSchemaMapper,
            dvaApi = dvaApi,
            dvaApiBaseUrl = dvaApiBaseUrl,
        )
    }

    @Provides
    @Singleton
    fun provideHealthCareStateRepository(
        healthCareRepository: HealthCareRepository,
        organizationRepository: OrganizationRepository,
    ): HealthCareStateRepository {
        return DefaultHealthCareStateRepository(
            healthCareRepository = healthCareRepository,
            organizationRepository = organizationRepository,
        )
    }
}
