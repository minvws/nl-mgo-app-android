package nl.rijksoverheid.mgo.data.pincode

import nl.rijksoverheid.mgo.data.pincode.hash.PinCodeHasher
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

internal class DefaultValidatePinCode
    @Inject
    constructor(
        @Named("secureKeyValueStore") private val keyValueStore: KeyValueStore,
        private val pinCodeHasher: PinCodeHasher,
    ) :
    ValidatePinCode {
        override suspend fun invoke(pinCode: List<Int>): Boolean {
            val pinCodeString = pinCode.joinToString(",")
            val storedPinCode = keyValueStore.getString(KEY_PIN_CODE) ?: return false
            return pinCodeHasher.validate(pinCodeString, storedPinCode)
        }
    }
