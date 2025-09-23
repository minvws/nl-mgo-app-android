package nl.rijksoverheid.mgo.data.healthData

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.healthData.configuration.DefaultHealthDataConfigurationRepository
import nl.rijksoverheid.mgo.data.healthData.configuration.HealthDataConfigurationRepository
import nl.rijksoverheid.mgo.data.healthData.fhir.DefaultFhirDataRepository
import nl.rijksoverheid.mgo.data.healthData.fhir.FhirDataRepository
import nl.rijksoverheid.mgo.data.healthData.health.DefaultHealthDataRepository
import nl.rijksoverheid.mgo.data.healthData.health.DefaultInitHealthDataFetching
import nl.rijksoverheid.mgo.data.healthData.health.HealthDataRepository
import nl.rijksoverheid.mgo.data.healthData.health.InitHealthDataFetching
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HealthDataModule {
  @Binds
  @Singleton
  abstract fun provideHealthDataConfigurationRepository(default: DefaultHealthDataConfigurationRepository): HealthDataConfigurationRepository

  @Binds
  @Singleton
  abstract fun provideFhirDataRepository(default: DefaultFhirDataRepository): FhirDataRepository

  @Binds
  @Singleton
  abstract fun provideHealthDataRepository(default: DefaultHealthDataRepository): HealthDataRepository

  @Binds
  @Singleton
  abstract fun provideInitHealthDataFetching(default: DefaultInitHealthDataFetching): InitHealthDataFetching
}
