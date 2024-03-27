package nl.rijksoverheid.mgo.framework.storage

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object StorageModule {
    @Provides
    @Singleton
    fun provideKeyValueStore(
        @ApplicationContext context: Context,
    ): KeyValueStore {
        return DataStoreKeyValueStore(context = context)
    }
}
