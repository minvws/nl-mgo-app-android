package nl.rijksoverheid.mgo.navigation.pincode

import kotlinx.serialization.Serializable

/**
 * Represents all navigation destinations when logging in with a pin code.
 */
sealed class PinCodeLoginNavigation {
  @Serializable
  data object Root : PinCodeLoginNavigation()

  @Serializable
  data object Login : PinCodeLoginNavigation()

  @Serializable
  data object LoginDialog : PinCodeLoginNavigation()

  @Serializable
  data object Forgot : PinCodeLoginNavigation()

  @Serializable
  data object Deleted : PinCodeLoginNavigation()
}
