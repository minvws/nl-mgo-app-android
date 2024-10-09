package nl.rijksoverheid.mgo.navigation.pincode

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.pincode.confirm.PinCodeConfirmScreen
import nl.rijksoverheid.mgo.feature.pincode.create.PinCodeCreateScreen
import nl.rijksoverheid.mgo.feature.pincode.login.PinCodeLoginScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigationScreen

fun NavGraphBuilder.addPinCodeNavGraph(
    navController: NavController,
    hasPinCode: Boolean,
) {
    navigation(
        startDestination = if (hasPinCode) PinCodeNavigationScreen.Login.getRoute() else PinCodeNavigationScreen.Create.getRoute(),
        route = PinCodeNavigationScreen.Start.getRoute(),
    ) {
        composableWithDefaultScreenTransitions(route = PinCodeNavigationScreen.Create.getRoute()) {
            PinCodeCreateScreen(
                onPinEntered = { pinCode ->
                    navController.navigate(PinCodeNavigationScreen.Confirm.setPinCodeToMatch(pinCode).getNavigationRoute())
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composableWithDefaultScreenTransitions(route = PinCodeNavigationScreen.Confirm.getRoute()) { backStackEntry ->
            PinCodeConfirmScreen(
                pinCodeToMatch = PinCodeNavigationScreen.Confirm.getPinCodeToMatch(backStackEntry),
                onPinConfirmed = {
                    navController.navigate(DashboardNavigationScreen.Start.getNavigationRoute()) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composableWithDefaultScreenTransitions(route = PinCodeNavigationScreen.Login.getRoute()) {
            PinCodeLoginScreen(
                onPinEntered = {
                    navController.navigate(DashboardNavigationScreen.Start.getNavigationRoute()) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}
