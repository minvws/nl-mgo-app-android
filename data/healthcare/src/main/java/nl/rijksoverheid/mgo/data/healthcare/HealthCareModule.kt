package nl.rijksoverheid.mgo.data.healthcare

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.healthcare.binary.DefaultHealthCareBinaryRepository
import nl.rijksoverheid.mgo.data.healthcare.binary.HealthCareBinaryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HealthCareModule {
    @Binds
    @Singleton
    abstract fun provideUiSchemaRepository(default: DefaultUiSchemaRepository): UiSchemaRepository

    @Binds
    @Singleton
    abstract fun provideHealthCareDataStateRepository(default: DefaultHealthCareDataStateRepository): HealthCareDataStateRepository

    @Binds
    @Singleton
    abstract fun provideHealthCareDataStatesRepository(default: DefaultHealthCareDataStatesRepository): HealthCareDataStatesRepository

    @Binds
    @Singleton
    abstract fun provideHealthCareBinaryRepository(default: DefaultHealthCareBinaryRepository): HealthCareBinaryRepository
}
