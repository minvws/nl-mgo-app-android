package nl.rijksoverheid.mgo.data.localisation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalisationModule {
  @Binds
  @Singleton
  abstract fun bindOrganizationRepository(default: DefaultOrganizationRepository): OrganizationRepository
}
