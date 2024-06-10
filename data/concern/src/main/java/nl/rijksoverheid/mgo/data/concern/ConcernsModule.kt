package nl.rijksoverheid.mgo.data.concern

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ConcernsModule {
    @Provides
    @Singleton
    fun provideConcernsRepository(dvaApi: DvaApi): ConcernsRepository {
        return DefaultConcernsRepository(dvaApi = dvaApi)
    }
}
