package nl.rijksoverheid.mgo.data.digid

/**
 * Handles authenticating with DigiD.
 */
interface DigidRepository {
  suspend fun login(): Result<String>
}
