package nl.rijksoverheid.mgo.data.onboarding

/**
 * Use case to check if the onboarding has been seen.
 */
interface HasSeenOnboarding {
    /**
     * @return True if the onboarding has been seen.
     */
    operator fun invoke(): Boolean
}
