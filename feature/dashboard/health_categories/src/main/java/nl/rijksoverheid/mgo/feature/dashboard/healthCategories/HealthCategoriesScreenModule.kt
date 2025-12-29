package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.component.error.DefaultGetErrorBanner
import nl.rijksoverheid.mgo.component.error.GetErrorBanner
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HealthCategoriesScreenModule {
  @Binds
  @Singleton
  abstract fun bindGetErrorBanner(default: DefaultGetErrorBanner): GetErrorBanner
}
