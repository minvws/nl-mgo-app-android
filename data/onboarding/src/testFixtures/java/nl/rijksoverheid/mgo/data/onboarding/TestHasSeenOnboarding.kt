package nl.rijksoverheid.mgo.data.onboarding

class TestHasSeenOnboarding : HasSeenOnboarding {
  private var hasSeen: Boolean = false

  fun set(hasSeen: Boolean) {
    this.hasSeen = hasSeen
  }

  override fun invoke(): Boolean {
    return hasSeen
  }
}
