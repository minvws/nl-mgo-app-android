package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.lifecycle.ViewModel
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

        fun validatePinCode(pinCode: List<Int>) {
            if (pinCode == pinCodeToMatch) {
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

        fun resetError() {
            _viewState.update { viewState ->
                viewState.copy(error = false)
            }
        }
    }
