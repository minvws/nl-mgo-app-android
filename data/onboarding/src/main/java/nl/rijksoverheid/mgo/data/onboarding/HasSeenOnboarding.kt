package nl.rijksoverheid.mgo.data.onboarding

interface HasSeenOnboarding {
    operator fun invoke(): Boolean
}
