package nl.rijksoverheid.mgo.framework.storage

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NewStorageBindsModule {
  @Binds
  @Singleton
  @Named("encryptedFileStorage")
  abstract fun bindEncryptedFileStorage(default: EncryptedMgoStorage): MgoStorage

  @Binds
  @Singleton
  @Named("memoryFileStorage")
  abstract fun bindMemoryFileStorage(default: MemoryMgoStorage): MgoStorage
}
