package nl.rijksoverheid.mgo.data.digid

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DigidModule {
  @Binds
  @Singleton
  abstract fun provideIsDigidAuthenticated(default: DefaultIsDigidAuthenticated): IsDigidAuthenticated

  @Binds
  @Singleton
  abstract fun provideSetDigidAuthenticated(default: DefaultSetDigidAuthenticated): SetDigidAuthenticated

  @Binds
  @Singleton
  abstract fun provideDigidRepository(default: DefaultDigidRepository): DigidRepository
}
