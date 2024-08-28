package nl.rijksoverheid.mgo.data.medication

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object MedicationModule {
    @Provides
    @Singleton
    fun provideMedicationRepository(dvaApi: DvaApi, uiSchemaMapper: UiSchemaMapper): MedicationRepository {
        return DefaultMedicationRepository(dvaApi = dvaApi, uiSchemaMapper = uiSchemaMapper)
    }
}
