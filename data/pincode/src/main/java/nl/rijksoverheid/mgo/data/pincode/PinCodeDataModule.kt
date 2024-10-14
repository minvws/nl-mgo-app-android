package nl.rijksoverheid.mgo.data.pincode

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.pincode.hash.BcryptPinCodeHasher
import nl.rijksoverheid.mgo.data.pincode.hash.PinCodeHasher
import nl.rijksoverheid.mgo.data.pincode.strength.DefaultPinCodeStrengthStrengthValidator
import nl.rijksoverheid.mgo.data.pincode.strength.PinCodeStrengthValidator
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PinCodeDataModule {
    @Provides
    @Singleton
    fun provideStorePinCode(
        keyValueStore: KeyValueStore,
        pinCodeHasher: PinCodeHasher,
    ): StorePinCode {
        return DefaultStorePinCode(keyValueStore, pinCodeHasher)
    }

    @Provides
    @Singleton
    fun provideValidatePinCode(
        keyValueStore: KeyValueStore,
        pinCodeHasher: PinCodeHasher,
    ): ValidatePinCode {
        return DefaultValidatePinCode(keyValueStore, pinCodeHasher)
    }

    @Provides
    @Singleton
    fun provideHasPinCode(keyValueStore: KeyValueStore): HasPinCode {
        return DefaultHasPinCode(keyValueStore)
    }

    @Provides
    @Singleton
    fun providePinCodeHasher(): PinCodeHasher {
        return BcryptPinCodeHasher()
    }

    @Provides
    @Singleton
    fun providePinCodeStrengthValidator(): PinCodeStrengthValidator {
        return DefaultPinCodeStrengthStrengthValidator()
    }
}
