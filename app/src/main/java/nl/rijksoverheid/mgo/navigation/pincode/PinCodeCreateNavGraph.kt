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
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposable

fun NavGraphBuilder.addPinCodeCreateNavGraph(navController: NavController) {
    navigation<PinCodeCreateNavigation.Root>(PinCodeCreateNavigation.Create) {
        mgoComposable<PinCodeCreateNavigation.Create> {
            PinCodeCreateScreen(
                hasBackButton = remember { navController.previousBackStackEntry != null },
                onPinEntered = { pinCode ->
                    navController.navigate(PinCodeCreateNavigation.Confirm(pinCode))
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        mgoComposable<PinCodeCreateNavigation.Confirm> { backStackEntry ->
            val route = backStackEntry.toRoute<PinCodeCreateNavigation.Confirm>()
            PinCodeConfirmScreen(
                pinCodeToMatch = route.pinCode,
                onNavigate = { navigation ->
                    when (navigation) {
                        PinCodeConfirmScreenNextNavigation.BIOMETRIC -> {
                            navController.navigate(PinCodeCreateNavigation.BiometricSetup) {
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

        mgoComposable<PinCodeCreateNavigation.BiometricSetup> {
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
    }
}
