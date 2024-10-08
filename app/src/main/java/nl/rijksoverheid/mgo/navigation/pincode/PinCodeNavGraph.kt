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
                onPinEntered = {
                    navController.navigate(PinCodeNavigationScreen.Confirm.getNavigationRoute())
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
        composableWithDefaultScreenTransitions(route = PinCodeNavigationScreen.Confirm.getRoute()) {
            PinCodeConfirmScreen(
                onPinConfirmed = {
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
