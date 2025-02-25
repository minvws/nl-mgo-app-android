package nl.rijksoverheid.mgo.data.digid

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_DIGID_AUTHENTICATED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.runBlocking

/**
 * Use case that sets if the user has authenticated with DigiD.
 * @param keyValueStore Store to save a key value pair into.
 */
internal class DefaultSetDigidAuthenticated
    @Inject
    constructor(
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) : SetDigidAuthenticated {
        override operator fun invoke() {
            runBlocking { keyValueStore.setBoolean(KEY_DIGID_AUTHENTICATED, true) }
        }
    }
