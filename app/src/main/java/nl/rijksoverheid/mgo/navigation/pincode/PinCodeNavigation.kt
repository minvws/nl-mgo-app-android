package nl.rijksoverheid.mgo.navigation.pincode

import kotlinx.serialization.Serializable

sealed class PinCodeNavigation {
    @Serializable
    data object Root

    @Serializable
    data object Create

    @Serializable
    data class Confirm(val pinCode: List<Int>)

    @Serializable
    data object BiometricSetup

    @Serializable
    data object Login

    @Serializable
    data object Forgot
}
