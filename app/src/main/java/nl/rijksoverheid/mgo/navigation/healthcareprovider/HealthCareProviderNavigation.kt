package nl.rijksoverheid.mgo.navigation.healthcareprovider

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.overview.OverviewScreen
import nl.rijksoverheid.mgo.feature.healthcareprovider.concern.ConcernScreen
import nl.rijksoverheid.mgo.feature.healthcareprovider.details.HealthCareProviderDetailsScreen
import nl.rijksoverheid.mgo.feature.healthcareprovider.medication.MedicationScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen

@Composable
fun HealthCareProviderNavigation(
    rootNavController: NavHostController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = HealthCareProviderNavigationScreen.Overview.getRoute(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composableWithDefaultScreenTransitions(HealthCareProviderNavigationScreen.Overview.getRoute()) {
            OverviewScreen(
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                },
                onNavigateToHealthCareProvider = { providerName, providerCategory ->
                    navController.navigate(
                        HealthCareProviderNavigationScreen.Details.setProviderName(
                            providerName,
                        ).setProviderCategory(providerCategory).getNavigationRoute(),
                    )
                },
            )
        }

        composableWithDefaultScreenTransitions(route = HealthCareProviderNavigationScreen.Details.getRoute()) { backStackEntry ->
            HealthCareProviderDetailsScreen(
                providerName = HealthCareProviderNavigationScreen.Details.getProviderName(backStackEntry),
                providerCategory = HealthCareProviderNavigationScreen.Details.getProviderCategory(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToMedication = { providerName ->
                    navController.navigate(HealthCareProviderNavigationScreen.Medication.setProviderName(providerName).getNavigationRoute())
                },
                onNavigateToConcern = {
                    navController.navigate(HealthCareProviderNavigationScreen.Concern.getNavigationRoute())
                },
            )
        }

        composableWithDefaultScreenTransitions(HealthCareProviderNavigationScreen.Medication.getRoute()) { backStackEntry ->
            MedicationScreen(
                providerName = HealthCareProviderNavigationScreen.Medication.getProviderName(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composableWithDefaultScreenTransitions(HealthCareProviderNavigationScreen.Concern.getRoute()) {
            ConcernScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
