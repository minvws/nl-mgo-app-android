package nl.rijksoverheid.mgo.module

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import nl.rijksoverheid.mgo.framework.environment.EnvironmentModule
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import javax.inject.Singleton

@Module
@TestInstallIn(
  components = [SingletonComponent::class],
  replaces = [EnvironmentModule::class],
)
object TestEnvironmentModule {
  @Provides
  @Singleton
  fun provideEnvironmentRepository(): EnvironmentRepository {
    return TestEnvironmentRepository()
  }
}
