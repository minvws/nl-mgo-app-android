package nl.rijksoverheid.mgo.data.onboarding

/**
 * Use case to set that the onboarding has been seen.
 */
interface SetHasSeenOnboarding {
    /**
     * @param hasSeen If the onboarding has been seen
     */
    operator fun invoke(hasSeen: Boolean)
}
