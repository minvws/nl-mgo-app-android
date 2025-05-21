package nl.rijksoverheid.mgo.navigation.digid

import kotlinx.serialization.Serializable

/**
 * Represents all navigation destinations when logging in with DigiD.
 */
sealed class DigidNavigation {
  @Serializable
  data object Root : DigidNavigation()

  @Serializable
  data object Login : DigidNavigation()

  @Serializable
  data object Mock : DigidNavigation()
}
