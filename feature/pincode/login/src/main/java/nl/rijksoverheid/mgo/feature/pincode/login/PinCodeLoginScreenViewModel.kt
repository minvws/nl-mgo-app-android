package nl.rijksoverheid.mgo.feature.pincode.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.pincode.ValidatePinCode
import nl.rijksoverheid.mgo.data.pincode.biometric.LoginWithBiometricEnabled
import nl.rijksoverheid.mgo.framework.copy.R
import javax.inject.Inject
import kotlinx.coroutines.delay
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

        init {
            viewModelScope.launch {
                delay(250)
                validatePinCode(listOf(1, 2, 3, 4, 9))
            }
        }

        fun validatePinCode(pinCode: List<Int>) {
            viewModelScope.launch {
                val validated = validatePinCode.invoke(pinCode)
                if (validated) {
                    _navigateToDashboard.tryEmit(Unit)
                } else {
                    _viewState.update { viewState ->
                        viewState.copy(error = true, subHeading = R.string.pincode_validation_wrong)
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
