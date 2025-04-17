package nl.rijksoverheid.mgo.framework.environment

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class EnvironmentModule {
  @Binds
  @Singleton
  abstract fun provideEnvironmentRepository(default: DefaultEnvironmentRepository): EnvironmentRepository
}
