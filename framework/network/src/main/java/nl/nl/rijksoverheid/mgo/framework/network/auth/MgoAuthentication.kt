package nl.nl.rijksoverheid.mgo.framework.network.auth

sealed class MgoAuthentication {
    data object None : MgoAuthentication()

    data class Basic(val user: String, val password: String) : MgoAuthentication()
}
