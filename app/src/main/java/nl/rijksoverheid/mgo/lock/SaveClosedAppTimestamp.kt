package nl.rijksoverheid.mgo.lock

interface SaveClosedAppTimestamp {
    suspend operator fun invoke()
}
