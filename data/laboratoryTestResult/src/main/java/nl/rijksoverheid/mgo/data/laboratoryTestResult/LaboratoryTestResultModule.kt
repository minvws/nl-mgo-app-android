package nl.rijksoverheid.mgo.data.laboratoryTestResult

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object LaboratoryTestResultModule {
    @Provides
    @Singleton
    fun provideLaboratoryTestResultRepository(dvaApi: DvaApi): LaboratoryTestResultRepository {
        return DefaultLaboratoryTestResultRepository(dvaApi = dvaApi)
    }
}
