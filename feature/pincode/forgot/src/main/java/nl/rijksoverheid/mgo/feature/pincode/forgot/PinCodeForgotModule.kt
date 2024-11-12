package nl.rijksoverheid.mgo.feature.pincode.forgot

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.feature.pincode.forgot.reset.DefaultResetPinCode
import nl.rijksoverheid.mgo.feature.pincode.forgot.reset.ResetPinCode
import nl.rijksoverheid.mgo.framework.storage.file.FileStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object PinCodeForgotModule {
    @Provides
    @Singleton
    fun provideResetPinCode(
        fileStore: FileStore,
        @Named("keyValueStore") keyValueStore: KeyValueStore,
        @Named("secureKeyValueStore") secureKeyValueStore: KeyValueStore,
    ): ResetPinCode {
        return DefaultResetPinCode(
            fileStore = fileStore,
            keyValueStore = keyValueStore,
            secureKeyValueStore = secureKeyValueStore,
        )
    }
}
