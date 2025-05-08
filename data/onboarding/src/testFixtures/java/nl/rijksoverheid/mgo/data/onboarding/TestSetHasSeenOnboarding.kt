package nl.rijksoverheid.mgo.data.onboarding

class TestSetHasSeenOnboarding : SetHasSeenOnboarding {
  private var hasSeen: Boolean = false

  fun get(): Boolean {
    return hasSeen
  }

  override fun invoke(hasSeen: Boolean) {
    this.hasSeen = true
  }
}
