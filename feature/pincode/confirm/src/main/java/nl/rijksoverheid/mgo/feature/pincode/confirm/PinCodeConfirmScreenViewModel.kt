package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.pincode.StorePinCode
import nl.rijksoverheid.mgo.data.pincode.biometric.DeviceHasBiometric
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@HiltViewModel(assistedFactory = PinCodeConfirmScreenViewModel.Factory::class)
internal class PinCodeConfirmScreenViewModel
    @AssistedInject
    constructor(
        @Assisted("pinCodeToMatch") private val pinCodeToMatch: List<Int>,
        private val storePinCode: StorePinCode,
        private val deviceHasBiometric: DeviceHasBiometric,
    ) :
    ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(
                @Assisted("pinCodeToMatch") pinCodeToMatch: List<Int>,
            ): PinCodeConfirmScreenViewModel
        }

        private val _viewState = MutableStateFlow(PinCodeConfirmScreenViewState.initialState)
        val viewState = _viewState.asStateFlow()

        private val _navigate = MutableSharedFlow<PinCodeConfirmScreenNextNavigation>(extraBufferCapacity = 1)
        val navigate = _navigate.asSharedFlow()

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
                    if (_viewState.value.pinCode.size == 5) {
                        if (_viewState.value.pinCode == pinCodeToMatch) {
                            storePinCode.invoke(pinCodeToMatch)
                            if (deviceHasBiometric()) {
                                _navigate.tryEmit(PinCodeConfirmScreenNextNavigation.BIOMETRIC)
                            } else {
                                _navigate.tryEmit(PinCodeConfirmScreenNextNavigation.DASHBOARD)
                            }
                        } else {
                            _viewState.update { viewState ->
                                viewState.copy(error = true, subHeading = CopyR.string.pincode_confirm_mismatch)
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
