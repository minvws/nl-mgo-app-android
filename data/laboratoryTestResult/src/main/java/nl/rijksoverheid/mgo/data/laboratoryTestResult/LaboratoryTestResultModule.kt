package nl.rijksoverheid.mgo.data.laboratoryTestResult

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object LaboratoryTestResultModule {
    @Provides
    @Singleton
    fun provideLaboratoryTestResultRepository(
        dvaApi: DvaApi,
        @Named("dvaApiBaseUrl") dvaApiBaseUrl: String,
    ): LaboratoryTestResultRepository {
        return DefaultLaboratoryTestResultRepository(dvaApi = dvaApi, dvaApiBaseUrl = dvaApiBaseUrl)
    }
}
