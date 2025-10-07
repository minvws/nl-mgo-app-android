package nl.rijksoverheid.mgo.data.healthCategories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HealthCategoriesModule {
  @Binds
  @Singleton
  abstract fun bindGetHealthCategoriesFromDisk(default: AndroidGetHealthCategoriesFromDisk): GetHealthCategoriesFromDisk

  @Binds
  @Singleton
  abstract fun bindGetDataSetsFromDisk(default: AndroidGetDataSetsFromDisk): GetDataSetsFromDisk
}
