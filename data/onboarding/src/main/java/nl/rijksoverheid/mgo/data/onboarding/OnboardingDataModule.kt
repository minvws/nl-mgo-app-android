package nl.rijksoverheid.mgo.data.onboarding

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import nl.rijksoverheid.mgo.framework.storage.KeyValueStore

@InstallIn(ViewModelComponent::class)
@Module
internal object OnboardingDataModule {
    @Provides
    @ViewModelScoped
    fun provideHasSeenOnboarding(keyValueStore: KeyValueStore): HasSeenOnboarding {
        return DefaultHasSeenOnboarding(keyValueStore)
    }

    @Provides
    @ViewModelScoped
    fun provideSetHasSeenOnboarding(keyValueStore: KeyValueStore): SetHasSeenOnboarding {
        return DefaultSetHasSeenOnboarding(keyValueStore)
    }
}
