package nl.rijksoverheid.mgo.lock

/**
 * Use case that checks if the app should be locked.
 */
interface AppLocked {
  /**
   * @return True if the app should be locked.
   */
  suspend operator fun invoke(): Boolean
}
