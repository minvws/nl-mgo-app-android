package nl.rijksoverheid.mgo.navigation.pincode

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.pincode.biometric.PinCodeBioMetricSetupScreen
import nl.rijksoverheid.mgo.feature.pincode.confirm.PinCodeConfirmScreen
import nl.rijksoverheid.mgo.feature.pincode.confirm.PinCodeConfirmScreenNextNavigation
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
                hasBackButton = remember { navController.previousBackStackEntry != null },
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
                onNavigate = { navigation ->
                    when (navigation) {
                        PinCodeConfirmScreenNextNavigation.BIOMETRIC -> {
                            navController.navigate(PinCodeNavigationScreen.BioMetricSetup.getNavigationRoute()) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                        PinCodeConfirmScreenNextNavigation.DASHBOARD -> {
                            navController.navigate(DashboardNavigationScreen.Start.getNavigationRoute()) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composableWithDefaultScreenTransitions(route = PinCodeNavigationScreen.BioMetricSetup.getRoute()) { backStackEntry ->
            PinCodeBioMetricSetupScreen(
                onNavigateToDashboard = {
                    navController.navigate(DashboardNavigationScreen.Start.getNavigationRoute()) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composableWithDefaultScreenTransitions(route = PinCodeNavigationScreen.Login.getRoute()) {
            PinCodeLoginScreen(
                onPinValidated = {
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
