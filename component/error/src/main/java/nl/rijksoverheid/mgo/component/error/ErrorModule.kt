package nl.rijksoverheid.mgo.component.error

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ErrorModule {
  @Binds
  @ViewModelScoped
  abstract fun bindGetErrorBanner(default: DefaultGetErrorBanner): GetErrorBanner
}
