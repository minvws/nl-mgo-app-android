package nl.rijksoverheid.mgo.framework.storage

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.storage.bytearray.EncryptedMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoByteArrayStorage
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StorageBindsModule {
  @Binds
  @Singleton
  @Named("encryptedFileStorage")
  abstract fun bindEncryptedFileStorage(default: EncryptedMgoByteArrayStorage): MgoByteArrayStorage

  @Binds
  @Singleton
  @Named("memoryFileStorage")
  abstract fun bindMemoryFileStorage(default: MemoryMgoByteArrayStorage): MgoByteArrayStorage
}
