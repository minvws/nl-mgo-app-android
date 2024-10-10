package nl.rijksoverheid.mgo.data.pincode

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

internal class DefaultHasPinCode
    @Inject
    constructor(private val keyValueStore: KeyValueStore) : HasPinCode {
        override fun invoke(): Boolean {
            return runBlocking { keyValueStore.getString(KEY_PIN_CODE) } != null
        }
    }
