package nl.rijksoverheid.mgo.data.healthcare

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.healthcare.binary.DefaultHealthCareBinaryRepository
import nl.rijksoverheid.mgo.data.healthcare.binary.HealthCareBinaryRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.DefaultHealthCareDataRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareDataRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.urlCreator.DefaultHealthCareUrlCreator
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.urlCreator.HealthCareUrlCreator
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.DefaultHealthCareDataStateRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataStateRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.DefaultHealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.HealthCareDataStatesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HealthCareModule {
    @Binds
    @Singleton
    abstract fun provideHealthCareDataRepository(default: DefaultHealthCareDataRepository): HealthCareDataRepository

    @Binds
    @Singleton
    abstract fun provideHealthCareDataStateRepository(default: DefaultHealthCareDataStateRepository): HealthCareDataStateRepository

    @Binds
    @Singleton
    abstract fun provideHealthCareDataStatesRepository(default: DefaultHealthCareDataStatesRepository): HealthCareDataStatesRepository

    @Binds
    @Singleton
    abstract fun provideHealthCareBinaryRepository(default: DefaultHealthCareBinaryRepository): HealthCareBinaryRepository

    @Binds
    @Singleton
    abstract fun provideHealthCareUrlCreator(default: DefaultHealthCareUrlCreator): HealthCareUrlCreator
}
