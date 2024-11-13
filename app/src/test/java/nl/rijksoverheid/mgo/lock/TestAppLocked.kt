package nl.rijksoverheid.mgo.lock

internal class TestAppLocked(private val locked: Boolean) : AppLocked {
    override suspend fun invoke(): Boolean {
        return locked
    }
}
