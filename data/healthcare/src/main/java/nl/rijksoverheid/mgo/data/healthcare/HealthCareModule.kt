package nl.rijksoverheid.mgo.data.healthcare

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HealthCareModule {
    @Provides
    @Singleton
    fun provideUiSchemaRepository(
        uiSchemaMapper: UiSchemaMapper,
        dvaApi: DvaApi,
        @Named("dvaApiBaseUrl") dvaApiBaseUrl: String,
    ): UiSchemaRepository {
        return DefaultUiSchemaRepository(
            uiSchemaMapper = uiSchemaMapper,
            dvaApi = dvaApi,
            dvaApiBaseUrl = dvaApiBaseUrl,
        )
    }

    @Provides
    @Singleton
    fun provideHealthCareDataStateRepository(uiSchemaRepository: UiSchemaRepository): HealthCareDataStateRepository {
        return DefaultHealthCareDataStateRepository(
            uiSchemaRepository = uiSchemaRepository,
        )
    }

    @Provides
    @Singleton
    fun provideHealthCareDataStatesRepository(
        healthCareDataStateRepository: HealthCareDataStateRepository,
    ): HealthCareDataStatesRepository {
        return DefaultHealthCareDataStatesRepository(
            healthCareDataStateRepository = healthCareDataStateRepository,
        )
    }
}
