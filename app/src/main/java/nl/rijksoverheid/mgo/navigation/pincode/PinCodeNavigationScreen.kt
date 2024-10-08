package nl.rijksoverheid.mgo.navigation.pincode

import nl.rijksoverheid.mgo.navigation.NavigationScreen

sealed class PinCodeNavigationScreen(override val name: String, override val placeholders: List<String> = listOf()) : NavigationScreen(
    name,
    placeholders,
) {
    data object Start : PinCodeNavigationScreen("pin-code-start")

    data object Create : PinCodeNavigationScreen("pin-code-create")

    data object Confirm : PinCodeNavigationScreen("pin-code-confirm")

    data object Login : PinCodeNavigationScreen("pin-code-login")
}
