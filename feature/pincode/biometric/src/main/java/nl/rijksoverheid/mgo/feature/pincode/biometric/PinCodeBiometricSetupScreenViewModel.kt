package nl.rijksoverheid.mgo.feature.pincode.biometric

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.pincode.biometric.SetLoginWithBiometricEnabled
import javax.inject.Inject

@HiltViewModel
internal class PinCodeBiometricSetupScreenViewModel
    @Inject
    constructor(
        private val setLoginWithBiometricEnabled: SetLoginWithBiometricEnabled,
    ) : ViewModel() {
        fun setBiometricLoginEnabled() {
            setLoginWithBiometricEnabled.invoke()
        }
    }
