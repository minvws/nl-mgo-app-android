package nl.rijksoverheid.mgo.framework.storage

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.storage.bytearray.EncryptedMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MgoKeyValueStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.SharedPreferencesMgoKeyValueStorage
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StorageBindsModule {
  @Binds
  @Singleton
  @Named("encryptedMgoByteArrayStorage")
  abstract fun bindEncryptedMgoByteArrayStorage(default: EncryptedMgoByteArrayStorage): MgoByteArrayStorage

  @Binds
  @Singleton
  @Named("memoryMgoByteArrayStorage")
  abstract fun bindMemoryMgoByteArrayStorage(default: MemoryMgoByteArrayStorage): MgoByteArrayStorage

  @Binds
  @Singleton
  @Named("sharedPreferencesMgoKeyValueStorage")
  abstract fun bindSharedPreferencesMgoKeyValueStorage(default: SharedPreferencesMgoKeyValueStorage): MgoKeyValueStorage

  @Binds
  @Singleton
  @Named("memoryMgoKeyValueStorage")
  abstract fun bindMemoryMgoKeyValueStorage(default: MemoryMgoKeyValueStorage): MgoKeyValueStorage
}
