package nl.rijksoverheid.mgo.lock

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_CLOSED_TIMESTAMP
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import java.time.Clock
import javax.inject.Inject
import javax.inject.Named

private const val APP_LOCK_SECONDS = 120

internal class DefaultAppLocked
    @Inject
    constructor(
        private val clock: Clock,
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) : AppLocked {
        override suspend fun invoke(): Boolean {
            val currentTimestamp = clock.instant().epochSecond
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
