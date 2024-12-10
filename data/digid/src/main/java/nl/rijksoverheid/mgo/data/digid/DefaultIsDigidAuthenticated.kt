package nl.rijksoverheid.mgo.data.digid

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_DIGID_AUTHENTICATED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

internal class DefaultIsDigidAuthenticated
    @Inject
    constructor(
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) :
    IsDigidAuthenticated {
        override fun invoke(): Boolean {
            return keyValueStore.getBoolean(KEY_DIGID_AUTHENTICATED)
        }
    }
