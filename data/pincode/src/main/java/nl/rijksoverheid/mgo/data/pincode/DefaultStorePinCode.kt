package nl.rijksoverheid.mgo.data.pincode

import nl.rijksoverheid.mgo.data.pincode.hash.PinCodeHasher
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

internal class DefaultStorePinCode
    @Inject
    constructor(private val keyValueStore: KeyValueStore, private val pinCodeHasher: PinCodeHasher) : StorePinCode {
        override operator fun invoke(pinCode: List<Int>) {
            val pinCodeString = pinCode.joinToString(",")
            val hashedPinCode = pinCodeHasher.hash(pinCodeString)
            runBlocking { keyValueStore.setString(KEY_PIN_CODE, hashedPinCode) }
        }
    }
