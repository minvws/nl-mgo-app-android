package nl.rijksoverheid.mgo.data.digid

/**
 * Use case that sets if the user has authenticated with DigiD.
 */
interface SetDigidAuthenticated {
    operator fun invoke()
}
