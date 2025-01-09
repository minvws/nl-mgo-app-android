package nl.rijksoverheid.mgo.feature.pincode.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.pincode.ValidatePinCode
import nl.rijksoverheid.mgo.data.pincode.biometric.LoginWithBiometricEnabled
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

        fun validatePinCode(pinCode: List<Int>) {
            viewModelScope.launch(Dispatchers.IO) {
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

        fun resetError() {
            _viewState.update { viewState ->
                viewState.copy(error = false)
            }
        }
    }
