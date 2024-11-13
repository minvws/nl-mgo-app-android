package nl.rijksoverheid.mgo.lock

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_CLOSED_TIMESTAMP
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import java.time.Clock
import javax.inject.Inject
import javax.inject.Named

internal class DefaultSaveClosedAppTimestamp
    @Inject
    constructor(
        private val clock: Clock,
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) :
    SaveClosedAppTimestamp {
        override suspend fun invoke() {
            val currentTimestamp = clock.instant().epochSecond
            keyValueStore.setLong(KEY_APP_CLOSED_TIMESTAMP, currentTimestamp)
        }
    }
