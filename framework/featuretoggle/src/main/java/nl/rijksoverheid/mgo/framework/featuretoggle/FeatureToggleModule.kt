package nl.rijksoverheid.mgo.framework.featuretoggle

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.DefaultFeatureToggleRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FeatureToggleModule {
    @Binds
    abstract fun provideFeatureToggleRepository(default: DefaultFeatureToggleRepository): FeatureToggleRepository
}
