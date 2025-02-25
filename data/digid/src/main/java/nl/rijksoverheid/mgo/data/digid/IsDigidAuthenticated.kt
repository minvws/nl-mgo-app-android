package nl.rijksoverheid.mgo.data.digid

/**
 * Use case that checks if the user has authenticated with DigiD.
 */
interface IsDigidAuthenticated {
    /**
     * @return True if the user has authenticated with DigiD.
     */
    operator fun invoke(): Boolean
}
