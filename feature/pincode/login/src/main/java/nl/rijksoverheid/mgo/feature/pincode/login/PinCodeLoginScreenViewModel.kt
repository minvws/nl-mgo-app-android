package nl.rijksoverheid.mgo.feature.pincode.login

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.pincode.ValidatePinCode
import nl.rijksoverheid.mgo.framework.copy.R
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class PinCodeLoginScreenViewModel
    @Inject
    constructor(private val validatePinCode: ValidatePinCode) : ViewModel() {
        private val _viewState = MutableStateFlow(PinCodeLoginScreenViewState.initialState)
        val viewState = _viewState.asStateFlow()

        private val _navigateToDashboard = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val navigateToDashboard = _navigateToDashboard.asSharedFlow()

        fun resetPinCode() {
            _viewState.update { viewState ->
                viewState.copy(pinCode = listOf(), error = false)
            }
        }

        fun addPinCodeNumber(number: Int) {
            viewModelScope.launch {
                if (_viewState.value.pinCode.size != 5) {
                    _viewState.update { viewState ->
                        val newPinCode = viewState.pinCode.toMutableList().also { it.add(number) }
                        viewState.copy(pinCode = newPinCode)
                    }
                    val pinCode = _viewState.value.pinCode
                    if (_viewState.value.pinCode.size == 5) {
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
            }
        }

        @VisibleForTesting
        fun setPinCode(numbers: List<Int>) {
            _viewState.value = viewState.value.copy(pinCode = numbers)
        }
    }
