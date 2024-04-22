package nl.rijksoverheid.mgo.framework.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.storage.keyvalue.DataStoreKeyValueStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object StorageModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app")

    @Provides
    @Singleton
    fun provideKeyValueStore(
        @ApplicationContext context: Context,
    ): KeyValueStore {
        return DataStoreKeyValueStore(
            dataStore = context.dataStore,
        )
    }

    @Provides
    @Singleton
    @Named("storageMoshi")
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}
