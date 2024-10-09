package nl.rijksoverheid.mgo.navigation.pincode

import androidx.navigation.NavBackStackEntry
import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class PinCodeNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Start : PinCodeNavigationScreen("pin-code-start")

    data object Create : PinCodeNavigationScreen("pin-code-create")

    data object Confirm : PinCodeNavigationScreen(name = "pin-code-confirm", placeholders = listOf("pinCodeToMatch")) {
        fun setPinCodeToMatch(pinCode: List<Int>): Confirm {
            val pinCodeString = pinCode.joinToString(",")
            builder.addArgument(Confirm.placeholders[0], pinCodeString)
            return this
        }

        fun getPinCodeToMatch(backStackEntry: NavBackStackEntry): List<Int> {
            val pinCodeString = requireNotNull(backStackEntry.arguments?.getString(Confirm.placeholders[0]))
            return pinCodeString.split(",").map { it.toInt() }
        }
    }

    data object Login : PinCodeNavigationScreen("pin-code-login")
}
