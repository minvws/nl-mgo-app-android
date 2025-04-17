package nl.rijksoverheid.mgo.lock

internal class TestAppLocked : AppLocked {
  private var locked: Boolean = false

  fun set(locked: Boolean) {
    this.locked = locked
  }

  override suspend fun invoke(): Boolean {
    return locked
  }
}
