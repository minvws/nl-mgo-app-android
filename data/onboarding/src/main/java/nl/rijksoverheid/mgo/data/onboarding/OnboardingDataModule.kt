package nl.rijksoverheid.mgo.data.onboarding

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object OnboardingDataModule {
    @Provides
    @Singleton
    fun provideHasSeenOnboarding(keyValueStore: KeyValueStore): HasSeenOnboarding {
        return DefaultHasSeenOnboarding(keyValueStore)
    }

    @Provides
    @Singleton
    fun provideSetHasSeenOnboarding(keyValueStore: KeyValueStore): SetHasSeenOnboarding {
        return DefaultSetHasSeenOnboarding(keyValueStore)
    }
}
