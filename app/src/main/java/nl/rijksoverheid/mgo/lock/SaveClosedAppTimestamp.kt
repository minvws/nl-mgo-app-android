package nl.rijksoverheid.mgo.lock

/**
 * Use case that saves locally saves a timestamp. The timestamp represents the last time the app was closed.
 */
interface SaveClosedAppTimestamp {
  suspend operator fun invoke()
}
