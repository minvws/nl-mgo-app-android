package nl.rijksoverheid.mgo.data.digid

/**
 * Check if the user has authenticated with DigiD.
 */
interface IsDigidAuthenticated {
  /**
   * @return True if the user has authenticated with DigiD.
   */
  operator fun invoke(): Boolean
}
