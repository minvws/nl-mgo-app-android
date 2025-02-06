package nl.rijksoverheid.mgo.lock

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_CLOSED_TIMESTAMP
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
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
        @Named("secureKeyValueStore") private val secureKeyValueStore: KeyValueStore,
    ) : AppLocked {
        override suspend fun invoke(): Boolean {
            // Do not lock if there is no pin
            val hasPin = secureKeyValueStore.getString(KEY_PIN_CODE) != null
            if (!hasPin) {
                return false
            }

            // Lock if the app is closed for long enough
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
