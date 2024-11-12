package nl.rijksoverheid.mgo.feature.pincode.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.feature.pincode.forgot.reset.ResetPinCode
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class PinCodeForgotScreenViewModel
    @Inject
    constructor(
        private val resetPinCode: ResetPinCode,
    ) : ViewModel() {
        private val _navigateToPinCodeCreate = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val navigateToPinCodeCreate = _navigateToPinCodeCreate.asSharedFlow()

        fun createNewAccount() {
            viewModelScope.launch {
                resetPinCode.invoke()
                _navigateToPinCodeCreate.tryEmit(Unit)
            }
        }
    }
