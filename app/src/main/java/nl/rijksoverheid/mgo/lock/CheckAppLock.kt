package nl.rijksoverheid.mgo.lock

interface CheckAppLock {
    suspend operator fun invoke(): Boolean
}
