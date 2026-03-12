package nl.rijksoverheid.mgo.navigation.digid

import kotlinx.serialization.Serializable

sealed class DigidNavigation {
  @Serializable
  data object Root : DigidNavigation()

  @Serializable
  data object Login : DigidNavigation()
}
