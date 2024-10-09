package nl.rijksoverheid.mgo.feature.pincode.create

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class PinCodeCreateScreenViewModel
    @Inject
    constructor() : ViewModel() {
        private val _viewState = MutableStateFlow(PinCodeCreateScreenViewState.initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, PinCodeCreateScreenViewState.initialState)

        private val _navigateToConfirm = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val navigateToConfirm = _navigateToConfirm.asSharedFlow()

        fun resetPinCode() {
            _viewState.value = PinCodeCreateScreenViewState.initialState
        }

        fun addPinCodeNumber(number: Int) {
            viewModelScope.launch {
                if (_viewState.value.pinCode.size != 5) {
                    _viewState.update { viewState ->
                        val newPinCode = viewState.pinCode.toMutableList().also { it.add(number) }
                        viewState.copy(pinCode = newPinCode)
                    }
                    if (_viewState.value.pinCode.size == 5) {
                        _navigateToConfirm.tryEmit(Unit)
                    }
                }
            }
        }

        @VisibleForTesting
        fun setPinCode(numbers: List<Int>) {
            _viewState.value = viewState.value.copy(pinCode = numbers)
        }
    }
