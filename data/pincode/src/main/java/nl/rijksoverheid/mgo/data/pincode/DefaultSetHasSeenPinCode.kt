package nl.rijksoverheid.mgo.data.pincode

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

internal class DefaultSetHasSeenPinCode
    @Inject
    constructor(private val keyValueStore: KeyValueStore) : SetHasSeenPinCode {
        override operator fun invoke(hasSeen: Boolean) {
            runBlocking { keyValueStore.setBoolean(KEY_HAS_SEEN_PIN_CODE, hasSeen) }
        }
    }
