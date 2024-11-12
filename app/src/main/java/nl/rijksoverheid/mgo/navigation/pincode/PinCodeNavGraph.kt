package nl.rijksoverheid.mgo.navigation.pincode

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.feature.pincode.biometric.PinCodeBioMetricSetupScreen
import nl.rijksoverheid.mgo.feature.pincode.confirm.PinCodeConfirmScreen
import nl.rijksoverheid.mgo.feature.pincode.confirm.PinCodeConfirmScreenNextNavigation
import nl.rijksoverheid.mgo.feature.pincode.create.PinCodeCreateScreen
import nl.rijksoverheid.mgo.feature.pincode.forgot.PinCodeForgotScreen
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposable

fun NavGraphBuilder.addPinCodeNavGraph(navController: NavController) {
    navigation<PinCodeNavigation.Root>(PinCodeNavigation.Create) {
        mgoComposable<PinCodeNavigation.Create> {
            PinCodeCreateScreen(
                hasBackButton = remember { navController.previousBackStackEntry != null },
                onPinEntered = { pinCode ->
                    navController.navigate(PinCodeNavigation.Confirm(pinCode))
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        mgoComposable<PinCodeNavigation.Confirm> { backStackEntry ->
            val route = backStackEntry.toRoute<PinCodeNavigation.Confirm>()
            PinCodeConfirmScreen(
                pinCodeToMatch = route.pinCode,
                onNavigate = { navigation ->
                    when (navigation) {
                        PinCodeConfirmScreenNextNavigation.BIOMETRIC -> {
                            navController.navigate(PinCodeNavigation.BiometricSetup) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }

                        PinCodeConfirmScreenNextNavigation.DASHBOARD -> {
                            navController.navigate(DashboardNavigation.Root) {
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

        mgoComposable<PinCodeNavigation.BiometricSetup> {
            PinCodeBioMetricSetupScreen(
                onNavigateToDashboard = {
                    navController.navigate(DashboardNavigation.Root) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        mgoComposable<PinCodeNavigation.Forgot> {
            PinCodeForgotScreen(
                onNavigateToPinCodeCreate = {
                    navController.navigate(PinCodeNavigation.Create) {
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
    }
}
