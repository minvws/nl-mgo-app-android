package nl.rijksoverheid.mgo.data.digid

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class DigidModule {
  @Binds
  @ViewModelScoped
  abstract fun provideIsDigidAuthenticated(default: DefaultIsDigidAuthenticated): IsDigidAuthenticated

  @Binds
  @ViewModelScoped
  abstract fun provideSetDigidAuthenticated(default: DefaultSetDigidAuthenticated): SetDigidAuthenticated

  @Binds
  @ViewModelScoped
  abstract fun provideDigidRepository(default: DefaultDigidRepository): DigidRepository
}
