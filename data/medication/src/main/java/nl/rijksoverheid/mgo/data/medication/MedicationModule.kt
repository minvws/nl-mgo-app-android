package nl.rijksoverheid.mgo.data.medication

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object MedicationModule {
    @Provides
    @Singleton
    fun provideMedicationRepository(dvaApi: DvaApi): MedicationRepository {
        return DefaultMedicationRepository(dvaApi = dvaApi)
    }
}
