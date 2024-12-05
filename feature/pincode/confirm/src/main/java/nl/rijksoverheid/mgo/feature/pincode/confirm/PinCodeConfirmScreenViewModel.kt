package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.pincode.StorePinCode
import nl.rijksoverheid.mgo.data.pincode.biometric.DeviceHasBiometric
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Named
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = PinCodeConfirmScreenViewModel.Factory::class)
internal class PinCodeConfirmScreenViewModel
    @AssistedInject
    constructor(
        @Assisted("pinCodeToMatch") private val pinCodeToMatch: List<Int>,
        private val storePinCode: StorePinCode,
        private val deviceHasBiometric: DeviceHasBiometric,
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
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
                } else if (keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION)) {
                    _navigate.tryEmit(PinCodeConfirmScreenNextNavigation.AUTOMATIC_LOCALISATION)
                } else {
                    _navigate.tryEmit(PinCodeConfirmScreenNextNavigation.DASHBOARD)
                }
            } else {
                _viewState.update { viewState ->
                    viewState.copy(error = true)
                }
            }
        }

        fun resetError() {
            _viewState.update { viewState ->
                viewState.copy(error = false)
            }
        }
    }
