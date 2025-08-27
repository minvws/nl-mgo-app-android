package nl.rijksoverheid.mgo.data.healthcare

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.healthcare.binary.DefaultFhirBinaryRepository
import nl.rijksoverheid.mgo.data.healthcare.binary.FhirBinaryRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.DefaultHealthCareDataStateRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataStateRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.DefaultHealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.HealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store.DefaultHealthCareDataStatesStore
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.store.HealthCareDataStatesStore
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.DefaultMgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.DefaultHealthCareCategoriesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoriesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator.DefaultHealthCareUrlCreator
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator.HealthCareUrlCreator
import nl.rijksoverheid.mgo.data.healthcare.models.mapper.DefaultUISchemaSectionMapper
import nl.rijksoverheid.mgo.data.healthcare.models.mapper.UISchemaSectionMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HealthCareModule {
  @Binds
  @Singleton
  abstract fun provideHealthCareDataStateRepository(default: DefaultHealthCareDataStateRepository): HealthCareDataStateRepository

  @Binds
  @Singleton
  abstract fun provideHealthCareDataStatesRepository(default: DefaultHealthCareDataStatesRepository): HealthCareDataStatesRepository

  @Binds
  @Singleton
  abstract fun provideFhirBinaryRepository(default: DefaultFhirBinaryRepository): FhirBinaryRepository

  @Binds
  @Singleton
  abstract fun provideHealthCareUrlCreator(default: DefaultHealthCareUrlCreator): HealthCareUrlCreator

  @Binds
  @Singleton
  abstract fun provideMgoResourceRepository(default: DefaultMgoResourceRepository): MgoResourceRepository

  @Binds
  @Singleton
  abstract fun provideHealthCareDataStatesStore(default: DefaultHealthCareDataStatesStore): HealthCareDataStatesStore

  @Binds
  @Singleton
  abstract fun provideUiSchemaSectionMapper(default: DefaultUISchemaSectionMapper): UISchemaSectionMapper

  @Binds
  @Singleton
  abstract fun provideHealthCareCategoriesRepository(default: DefaultHealthCareCategoriesRepository): HealthCareCategoriesRepository
}
