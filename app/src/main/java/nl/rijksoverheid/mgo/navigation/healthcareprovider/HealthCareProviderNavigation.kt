package nl.rijksoverheid.mgo.navigation.healthcareprovider

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.overview.OverviewScreen
import nl.rijksoverheid.mgo.feature.healthcareprovider.concern.ConcernScreen
import nl.rijksoverheid.mgo.feature.healthcareprovider.details.HealthCareProviderDetailsScreen
import nl.rijksoverheid.mgo.feature.healthcareprovider.laboratoryTestResult.LaboratoryTestResultScreen
import nl.rijksoverheid.mgo.feature.healthcareprovider.medication.MedicationScreen
import nl.rijksoverheid.mgo.feature.healthcareprovider.removeprovider.RemoveProviderScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.dialogWithDefaultScreenTransitions
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
                onNavigateToHealthCareProvider = { providerId, providerName, providerCategory ->
                    navController.navigate(
                        HealthCareProviderNavigationScreen.Details
                            .setProviderId(providerId)
                            .setProviderName(providerName)
                            .setProviderCategory(providerCategory)
                            .getNavigationRoute(),
                    )
                },
            )
        }

        composableWithDefaultScreenTransitions(route = HealthCareProviderNavigationScreen.Details.getRoute()) { backStackEntry ->
            val providerId = HealthCareProviderNavigationScreen.Details.getProviderId(backStackEntry)
            val providerName = HealthCareProviderNavigationScreen.Details.getProviderName(backStackEntry)
            val providerCategory = HealthCareProviderNavigationScreen.Details.getProviderCategory(backStackEntry)
            HealthCareProviderDetailsScreen(
                providerName = providerName,
                providerCategory = providerCategory,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToMedication = {
                    navController.navigate(HealthCareProviderNavigationScreen.Medication.setProviderName(providerName).getNavigationRoute())
                },
                onNavigateToConcern = {
                    navController.navigate(HealthCareProviderNavigationScreen.Concern.getNavigationRoute())
                },
                onNavigateToLaboratoryTestResult = {
                    navController.navigate(HealthCareProviderNavigationScreen.LaboratoryTestResult.getNavigationRoute())
                },
                onNavigateToRemoveProvider = {
                    navController.navigate(
                        HealthCareProviderNavigationScreen.RemoveProvider.setProviderId(providerId).setProviderName
                            (providerName).getNavigationRoute(),
                    )
                },
            )
        }

        dialogWithDefaultScreenTransitions(
            route = HealthCareProviderNavigationScreen.RemoveProvider.getRoute(),
        ) { backStackEntry ->
            RemoveProviderScreen(
                providerId = HealthCareProviderNavigationScreen.RemoveProvider.getProviderId(backStackEntry),
                providerName = HealthCareProviderNavigationScreen.RemoveProvider.getProviderName(backStackEntry),
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDashboard = {
                    navController.popBackStack(
                        route = HealthCareProviderNavigationScreen.Overview.getRoute(),
                        inclusive = false,
                    )
                },
            )
        }

        composableWithDefaultScreenTransitions(HealthCareProviderNavigationScreen.Medication.getRoute()) { backStackEntry ->
            MedicationScreen(
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

        composableWithDefaultScreenTransitions(HealthCareProviderNavigationScreen.LaboratoryTestResult.getRoute()) {
            LaboratoryTestResultScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
