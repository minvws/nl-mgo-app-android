package nl.rijksoverheid.mgo.feature.pincode.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.feature.pincode.forgot.reset.ResetPinCode
import javax.inject.Inject

/**
 * The [ViewModel] for [PinCodeForgotScreen].
 *
 * @param resetPinCode The [ResetPinCode] that resets the pin code.
 */
@HiltViewModel
internal class PinCodeForgotScreenViewModel
  @Inject
  constructor(
    private val resetPinCode: ResetPinCode,
  ) : ViewModel() {
    private val _navigateToPinCodeCreate = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateToPinCodeCreate = _navigateToPinCodeCreate.asSharedFlow()

    /**
     * Call to reset the pin code. In the context of the app this means creating a new account.
     * When the pin code is successfully reset, [navigateToPinCodeCreate] is updated.
     */
    fun createNewAccount() {
      viewModelScope.launch {
        resetPinCode.invoke()
        _navigateToPinCodeCreate.tryEmit(Unit)
      }
    }
  }
