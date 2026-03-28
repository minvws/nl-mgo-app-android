package nl.rijksoverheid.mgo.data.organization.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.organization.api.DefaultOrganizationApiClient
import nl.rijksoverheid.mgo.data.organization.api.OrganizationApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class OrganizationBindsModule {
  @Binds
  @Singleton
  abstract fun bindApiClient(default: DefaultOrganizationApiClient): OrganizationApiClient
}
