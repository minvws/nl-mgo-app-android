package nl.rijksoverheid.mgo.lock

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_CLOSED_TIMESTAMP
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import java.time.Clock
import javax.inject.Inject
import javax.inject.Named

private const val APP_LOCK_SECONDS = 120

/**
 * Use case that checks if the app should be locked.
 * @param clock The clock used to determine if the time has elapsed to lock the app.
 * @param keyValueStore The store to get the timestamp when the app has been closed.
 * @param secureKeyValueStore The store to get the pin code. If there is no pin code, the app will never be locked.
 */
internal class DefaultAppLocked
  @Inject
  constructor(
    private val clock: Clock,
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    @Named("secureKeyValueStore") private val secureKeyValueStore: KeyValueStore,
  ) : AppLocked {
    /**
     * @return True if the app should be locked.
     */
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
