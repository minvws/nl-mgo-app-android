package nl.rijksoverheid.mgo.data.digid

/**
 * Repository that handles authenticating with DigiD.
 */
interface DigidRepository {
    suspend fun login(): Result<String>
}
