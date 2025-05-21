package nl.rijksoverheid.mgo.feature.pincode.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.data.pincode.ValidatePinCode
import nl.rijksoverheid.mgo.data.pincode.biometric.LoginWithBiometricEnabled
import javax.inject.Inject

/**
 * The [ViewModel] for the [PinCodeLoginScreen].
 *
 * @param validatePinCode The [ValidatePinCode] used to validate the pin code that is entered against the stored pin code.
 * @param loginWithBiometricEnabled The [LoginWithBiometricEnabled] to check if login with biometric is enabled.
 */
@HiltViewModel
internal class PinCodeLoginScreenViewModel
  @Inject
  constructor(
    private val validatePinCode: ValidatePinCode,
    loginWithBiometricEnabled: LoginWithBiometricEnabled,
  ) : ViewModel() {
    private val _viewState = MutableStateFlow(PinCodeLoginScreenViewState.initialState(loginWithBiometricEnabled.invoke()))
    val viewState = _viewState.asStateFlow()

    private val _navigateToDashboard = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateToDashboard = _navigateToDashboard.asSharedFlow()

    /**
     * Validate the pin code that is entered against the pin code that is stored.
     * Updates [navigateToDashboard] when validated, else shows error in the UI.
     *
     * @param pinCode The entered pin code.
     */
    fun validatePinCode(pinCode: List<Int>) {
      viewModelScope.launch {
        val validated = validatePinCode.invoke(pinCode)
        if (validated) {
          _navigateToDashboard.tryEmit(Unit)
        } else {
          _viewState.update { viewState ->
            viewState.copy(error = true)
          }
        }
      }
    }

    /**
     * Reset the error message in the UI.
     */
    fun resetError() {
      _viewState.update { viewState ->
        viewState.copy(error = false)
      }
    }
  }
