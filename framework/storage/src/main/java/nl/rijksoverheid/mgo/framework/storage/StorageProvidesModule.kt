package nl.rijksoverheid.mgo.framework.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.storage.keyvalue.DataStoreKeyValueStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.EncryptedSharedPreferencesSecureKeyValueStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object StorageProvidesModule {
  private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app")

  @Singleton
  @Provides
  @Named("masterKeyAlias")
  fun provideMasterKeyAlias(): String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

  @Singleton
  @Provides
  fun provideSharedPreferences(
    @ApplicationContext context: Context,
  ): SharedPreferences = context.getSharedPreferences("mgo_shared_preferences", Context.MODE_PRIVATE)

  @Provides
  @Singleton
  @Named("keyValueStore")
  fun provideKeyValueStore(
    @ApplicationContext context: Context,
  ): KeyValueStore =
    DataStoreKeyValueStore(
      dataStore = context.dataStore,
    )

  @Provides
  @Singleton
  @Named("secureKeyValueStore")
  fun provideSecureKeyValueStore(
    @ApplicationContext context: Context,
  ): KeyValueStore {
    val encryptedSharedPreferences =
      EncryptedSharedPreferences.create(
        "app_secure",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
      )
    return EncryptedSharedPreferencesSecureKeyValueStore(encryptedSharedPreferences)
  }
}
