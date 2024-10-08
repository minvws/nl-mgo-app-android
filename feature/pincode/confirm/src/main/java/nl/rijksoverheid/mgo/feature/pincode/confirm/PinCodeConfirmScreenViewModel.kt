package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.pincode.SetHasSeenPinCode
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

@HiltViewModel
internal class PinCodeConfirmScreenViewModel
    @Inject
    constructor(
        private val setHasSeenPinCode: SetHasSeenPinCode,
    ) : ViewModel() {
        fun setHasSeenPinCode() {
            runBlocking { setHasSeenPinCode.invoke(true) }
        }
    }
