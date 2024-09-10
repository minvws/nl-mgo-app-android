package nl.rijksoverheid.mgo.data.healthcare

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HealthCareModule {
    @Provides
    @Singleton
    fun provideHealthCareRepository(
        organizationRepository: OrganizationRepository,
        uiSchemaMapper: UiSchemaMapper,
        dvaApi: DvaApi,
    ): HealthCareRepository {
        return DefaultHealthCareRepository(
            organizationRepository = organizationRepository,
            uiSchemaMapper = uiSchemaMapper,
            dvaApi = dvaApi,
        )
    }
}
