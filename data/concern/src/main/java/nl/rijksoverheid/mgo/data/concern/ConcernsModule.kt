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
    fun provideConcernRepository(dvaApi: DvaApi): ConcernRepository {
        return DefaultConcernRepository(dvaApi = dvaApi)
    }
}
