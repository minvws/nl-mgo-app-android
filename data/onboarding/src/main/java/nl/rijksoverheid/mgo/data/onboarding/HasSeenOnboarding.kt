package nl.rijksoverheid.mgo.data.onboarding

/**
 * Use case that checks if the user has finished the onboarding.
 */
interface HasSeenOnboarding {
    /**
     * @return True if the user has finished the onboarding.
     */
    operator fun invoke(): Boolean
}
