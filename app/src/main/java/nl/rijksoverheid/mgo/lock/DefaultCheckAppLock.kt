package nl.rijksoverheid.mgo.lock

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_CLOSED_TIMESTAMP
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import java.time.Instant
import javax.inject.Inject
import javax.inject.Named

private const val APP_LOCK_SECONDS = 120

internal class DefaultCheckAppLock
    @Inject
    constructor(
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) : CheckAppLock {
        override suspend fun invoke(): Boolean {
            val currentTimestamp = Instant.now().epochSecond
            val closedAppTimestamp = keyValueStore.getLong(KEY_APP_CLOSED_TIMESTAMP)
            if (closedAppTimestamp != null) {
                val closedAppSeconds = currentTimestamp - closedAppTimestamp
                if (closedAppSeconds >= APP_LOCK_SECONDS) {
                    return true
                }
            }
            return false
        }
    }
