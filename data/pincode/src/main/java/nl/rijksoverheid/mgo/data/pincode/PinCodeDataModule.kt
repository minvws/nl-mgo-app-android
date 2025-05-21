package nl.rijksoverheid.mgo.data.pincode

import android.content.Context
import androidx.biometric.BiometricManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.pincode.biometric.DefaultDeviceHasBiometric
import nl.rijksoverheid.mgo.data.pincode.biometric.DefaultLoginWithBiometricEnabled
import nl.rijksoverheid.mgo.data.pincode.biometric.DefaultSetLoginWithBiometricEnabled
import nl.rijksoverheid.mgo.data.pincode.biometric.DeviceHasBiometric
import nl.rijksoverheid.mgo.data.pincode.biometric.LoginWithBiometricEnabled
import nl.rijksoverheid.mgo.data.pincode.biometric.SetLoginWithBiometricEnabled
import nl.rijksoverheid.mgo.data.pincode.hash.BcryptPinCodeHasher
import nl.rijksoverheid.mgo.data.pincode.hash.PinCodeHasher
import nl.rijksoverheid.mgo.data.pincode.strength.DefaultPinCodeStrengthStrengthValidator
import nl.rijksoverheid.mgo.data.pincode.strength.PinCodeStrengthValidator
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PinCodeDataModule {
  @Provides
  @Singleton
  fun provideStorePinCode(
    @Named("secureKeyValueStore") keyValueStore: KeyValueStore,
    pinCodeHasher: PinCodeHasher,
  ): StorePinCode {
    return DefaultStorePinCode(keyValueStore, pinCodeHasher)
  }

  @Provides
  @Singleton
  fun provideValidatePinCode(
    @Named("secureKeyValueStore") keyValueStore: KeyValueStore,
    pinCodeHasher: PinCodeHasher,
  ): ValidatePinCode {
    return DefaultValidatePinCode(keyValueStore, pinCodeHasher)
  }

  @Provides
  @Singleton
  fun provideHasPinCode(
    @Named("secureKeyValueStore") keyValueStore: KeyValueStore,
  ): HasPinCode {
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

  @Provides
  @Singleton
  fun provideDeviceHasBiometric(
    @ApplicationContext context: Context,
  ): DeviceHasBiometric {
    val bioMetricManager = BiometricManager.from(context)
    return DefaultDeviceHasBiometric(bioMetricManager)
  }

  @Provides
  @Singleton
  fun provideLoginWithBiometricEnabled(
    @Named("keyValueStore") keyValueStore: KeyValueStore,
  ): LoginWithBiometricEnabled {
    return DefaultLoginWithBiometricEnabled(keyValueStore)
  }

  @Provides
  @Singleton
  fun provideSetLoginWithBiometricEnabled(
    @Named("keyValueStore") keyValueStore: KeyValueStore,
  ): SetLoginWithBiometricEnabled {
    return DefaultSetLoginWithBiometricEnabled(keyValueStore)
  }
}
