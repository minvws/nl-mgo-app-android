package nl.rijksoverheid.mgo.navigation.pincode

import kotlinx.serialization.Serializable

/**
 * Represents all navigation destinations when creating a pin code.
 */
sealed class PinCodeCreateNavigation {
  @Serializable
  data object Root : PinCodeCreateNavigation()

  @Serializable
  data object Create : PinCodeCreateNavigation()

  @Serializable
  data class Confirm(val pinCode: List<Int>) : PinCodeCreateNavigation()

  @Serializable
  data object BiometricSetup : PinCodeCreateNavigation()
}
