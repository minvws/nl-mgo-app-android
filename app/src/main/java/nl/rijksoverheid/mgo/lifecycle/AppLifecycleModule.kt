package nl.rijksoverheid.mgo.lifecycle

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AppLifecycleModule {
  @Binds
  @Singleton
  abstract fun bindAppLifecycleRepository(default: DefaultAppLifecycleRepository): AppLifecycleRepository
}
