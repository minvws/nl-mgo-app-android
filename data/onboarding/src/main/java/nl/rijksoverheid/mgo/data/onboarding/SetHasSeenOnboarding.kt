package nl.rijksoverheid.mgo.data.onboarding

interface SetHasSeenOnboarding {
    operator fun invoke(hasSeen: Boolean)
}
