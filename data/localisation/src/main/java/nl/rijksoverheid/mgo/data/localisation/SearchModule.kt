package nl.rijksoverheid.mgo.data.localisation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.api.load.LoadApi
import nl.rijksoverheid.mgo.framework.storage.file.FileStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object SearchModule {
    @Provides
    @Singleton
    fun provideSearchRepository(
        loadApi: LoadApi,
        fileStore: FileStore,
    ): HealthCareProviderRepository {
        return DefaultHealthCareProviderRepository(loadApi = loadApi, fileStore = fileStore)
    }
}
