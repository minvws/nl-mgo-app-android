package nl.rijksoverheid.mgo.lock

interface AppLocked {
    suspend operator fun invoke(): Boolean
}
