package nl.rijksoverheid.mgo.data.digid

interface DigidRepository {
    suspend fun login(): Result<String>
}
