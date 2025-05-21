package nl.nl.rijksoverheid.mgo.framework.network.auth

/**
 * Represents different authentication methods to communicate with the backend.
 */
sealed class MgoAuthentication {
  /**
   * Represents no authentication needed.
   */
  data object None : MgoAuthentication()

  /**
   * Represents Basic Authentication with a username and password.
   */
  data class Basic(val user: String, val password: String) : MgoAuthentication()
}
