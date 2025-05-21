package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.rijksoverheid.mgo.data.pincode.StorePinCode
import nl.rijksoverheid.mgo.data.pincode.biometric.DeviceHasBiometric

/**
 * The [ViewModel] for [PinCodeConfirmScreen].
 *
 * @param pinCodeToMatch The pin code entered in a previous screen that should match the pin code entered here.
 * @param storePinCode The [StorePinCode] that is used to store the pin code.
 * @param deviceHasBiometric The [DeviceHasBiometric] so check if the device supports biometric login.
 */
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

    /**
     * Validates the supplied pin code with the [pinCodeToMatch] passed one.
     * Navigates to another screen when successfully validated.
     * Updates the UI with an error if failed to validate.
     *
     * @param pinCode The pin code that was entered in the screen.
     */
    fun validatePinCode(pinCode: List<Int>) {
      if (pinCode == pinCodeToMatch) {
        storePinCode.invoke(pinCodeToMatch)
        if (deviceHasBiometric()) {
          _navigate.tryEmit(PinCodeConfirmScreenNextNavigation.BIOMETRIC)
        } else {
          _navigate.tryEmit(PinCodeConfirmScreenNextNavigation.DIGID)
        }
      } else {
        _viewState.update { viewState ->
          viewState.copy(error = true)
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
