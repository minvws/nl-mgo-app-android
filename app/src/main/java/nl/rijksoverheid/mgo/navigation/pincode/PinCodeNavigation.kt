package nl.rijksoverheid.mgo.navigation.pincode

import kotlinx.serialization.Serializable

sealed class PinCodeNavigation {
    @Serializable
    data object Root : PinCodeNavigation()

    @Serializable
    data object Create : PinCodeNavigation()

    @Serializable
    data class Confirm(val pinCode: List<Int>) : PinCodeNavigation()

    @Serializable
    data object BiometricSetup : PinCodeNavigation()

    @Serializable
    data object Login : PinCodeNavigation()

    @Serializable
    data object Forgot : PinCodeNavigation()
}
