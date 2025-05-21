package nl.rijksoverheid.mgo.feature.pincode.biometric

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.pincode.biometric.SetLoginWithBiometricEnabled
import javax.inject.Inject

/**
 * The [ViewModel] for [PinCodeBioMetricSetupScreen].
 *
 * @param setLoginWithBiometricEnabled The [SetLoginWithBiometricEnabled] to set that biometric login has been successfully enabled.
 */
@HiltViewModel
internal class PinCodeBiometricSetupScreenViewModel
  @Inject
  constructor(
    private val setLoginWithBiometricEnabled: SetLoginWithBiometricEnabled,
  ) : ViewModel() {
    /**
     * Call to set that biometric login has been successfully enabled.
     */
    fun setBiometricLoginEnabled() {
      setLoginWithBiometricEnabled.invoke()
    }
  }
