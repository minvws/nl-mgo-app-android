package nl.rijksoverheid.mgo.data.pincode

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PinCodeDataModule {
    @Provides
    @Singleton
    fun provideHasSeenPinCode(keyValueStore: KeyValueStore): HasSeenPinCode {
        return DefaultHasSeenPinCode(keyValueStore)
    }

    @Provides
    @Singleton
    fun provideSetHasSeenPinCode(keyValueStore: KeyValueStore): SetHasSeenPinCode {
        return DefaultSetHasSeenPinCode(keyValueStore)
    }
}
