package nl.rijksoverheid.mgo.framework.storage

import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NewStorageProvidesModule {
  @Singleton
  @Provides
  @Named("masterKeyAlias")
  fun provideMasterKeyAlias(): String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
}
