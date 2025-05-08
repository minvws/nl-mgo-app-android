package nl.rijksoverheid.mgo.lock

internal class TestSaveClosedAppTimestamp : SaveClosedAppTimestamp {
  var saved: Boolean = false

  override suspend fun invoke() {
    saved = true
  }
}
