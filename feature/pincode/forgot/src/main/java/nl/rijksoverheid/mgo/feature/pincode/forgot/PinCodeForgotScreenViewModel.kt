package nl.rijksoverheid.mgo.feature.pincode.forgot

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
internal class PinCodeForgotScreenViewModel
    @Inject
    constructor(
        @Named("secureKeyValueStore") private val keyValueStore: KeyValueStore,
    ) : ViewModel() {
        private val _navigateToPinCodeCreate = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val navigateToPinCodeCreate = _navigateToPinCodeCreate.asSharedFlow()

        fun createNewAccount() {
            keyValueStore.clear()
            _navigateToPinCodeCreate.tryEmit(Unit)
        }
    }
