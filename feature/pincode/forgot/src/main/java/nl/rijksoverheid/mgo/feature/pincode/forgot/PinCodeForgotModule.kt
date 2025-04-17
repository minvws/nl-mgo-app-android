package nl.rijksoverheid.mgo.feature.pincode.forgot

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nl.rijksoverheid.mgo.feature.pincode.forgot.reset.DefaultResetPinCode
import nl.rijksoverheid.mgo.feature.pincode.forgot.reset.ResetPinCode

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class PinCodeForgotModule {
  @Binds
  abstract fun provideResetPinCode(default: DefaultResetPinCode): ResetPinCode
}
