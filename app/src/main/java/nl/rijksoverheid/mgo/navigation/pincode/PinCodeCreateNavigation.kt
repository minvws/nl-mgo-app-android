package nl.rijksoverheid.mgo.navigation.pincode

import kotlinx.serialization.Serializable

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
