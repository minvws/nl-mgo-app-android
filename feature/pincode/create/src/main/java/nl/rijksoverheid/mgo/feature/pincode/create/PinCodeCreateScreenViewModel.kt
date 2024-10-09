package nl.rijksoverheid.mgo.feature.pincode.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class PinCodeCreateScreenViewModel
    @Inject
    constructor() : ViewModel() {
        private val _viewState = MutableStateFlow(PinCodeCreateScreenViewState.initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, PinCodeCreateScreenViewState.initialState)

        fun addPinCodeNumber(number: Int) {
            _viewState.update { viewState ->
                val newPinCode = viewState.pinCode.toMutableList()
                newPinCode.add(number)
                viewState.copy(pinCode = newPinCode)
            }
        }
    }
