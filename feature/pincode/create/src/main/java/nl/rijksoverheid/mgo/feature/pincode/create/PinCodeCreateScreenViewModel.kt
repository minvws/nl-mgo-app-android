package nl.rijksoverheid.mgo.feature.pincode.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.pincode.strength.PinCodeStrengthValidator
import nl.rijksoverheid.mgo.framework.copy.R
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class PinCodeCreateScreenViewModel
    @Inject
    constructor(
        private val validator: PinCodeStrengthValidator,
    ) : ViewModel() {
        private val _viewState = MutableStateFlow(PinCodeCreateScreenViewState.initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, PinCodeCreateScreenViewState.initialState)

        private val _navigateToConfirm = MutableSharedFlow<List<Int>>(extraBufferCapacity = 1)
        val navigateToConfirm = _navigateToConfirm.asSharedFlow()

        fun validatePinCode(pinCode: List<Int>) {
            val valid = validator.invoke(pinCode)
            if (valid) {
                _navigateToConfirm.tryEmit(pinCode)
            } else {
                _viewState.update { viewState ->
                    viewState.copy(error = true, subHeading = R.string.pincode_create_tooweak)
                }
            }
        }

        fun resetError() {
            _viewState.update { viewState ->
                viewState.copy(error = false)
            }
        }
    }
