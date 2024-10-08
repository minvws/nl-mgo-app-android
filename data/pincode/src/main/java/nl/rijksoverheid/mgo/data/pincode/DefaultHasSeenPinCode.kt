package nl.rijksoverheid.mgo.data.pincode

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

internal class DefaultHasSeenPinCode
    @Inject
    constructor(private val keyValueStore: KeyValueStore) : HasSeenPinCode {
        override operator fun invoke(): Boolean {
            return runBlocking { keyValueStore.getBoolean(KEY_HAS_SEEN_PIN_CODE) }
        }
    }
