package nl.rijksoverheid.mgo.data.digid

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_DIGID_AUTHENTICATED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * Use case that checks if the user has authenticated with DigiD.
 * @param keyValueStore Store to save a key value pair into.
 */
internal class DefaultIsDigidAuthenticated
    @Inject
    constructor(
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) :
    IsDigidAuthenticated {
        /**
         * @return True if the user has authenticated with DigiD.
         */
        override fun invoke(): Boolean {
            return keyValueStore.getBoolean(KEY_DIGID_AUTHENTICATED)
        }
    }
