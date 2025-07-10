package nl.rijksoverheid.mgo.lock

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_CLOSED_TIMESTAMP
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import java.time.Clock
import javax.inject.Inject
import javax.inject.Named

/**
 * Use case that saves locally saves a timestamp. The timestamp represents the last time the app was closed.
 * @param clock The clock used to determine the current timestamp.
 * @param keyValueStore The store to save the timestamp into.
 */
internal class DefaultSaveClosedAppTimestamp
  @Inject
  constructor(
    @Named("systemUTC") private val clock: Clock,
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
  ) : SaveClosedAppTimestamp {
    override suspend fun invoke() {
      val currentTimestamp = clock.instant().epochSecond
      keyValueStore.setLong(KEY_APP_CLOSED_TIMESTAMP, currentTimestamp)
    }
  }
