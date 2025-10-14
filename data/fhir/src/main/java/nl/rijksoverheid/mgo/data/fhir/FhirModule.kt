package nl.rijksoverheid.mgo.data.fhir

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FhirModule {
  @Binds
  @Singleton
  abstract fun bindFhirRepository(default: DefaultFhirRepository): FhirRepository
}
