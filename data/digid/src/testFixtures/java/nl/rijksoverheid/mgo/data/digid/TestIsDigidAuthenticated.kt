package nl.rijksoverheid.mgo.data.digid

class TestIsDigidAuthenticated : IsDigidAuthenticated {
  private var isAuthenticated = false

  fun set(isAuthenticated: Boolean) {
    this.isAuthenticated = isAuthenticated
  }

  override fun invoke(): Boolean {
    return isAuthenticated
  }
}
