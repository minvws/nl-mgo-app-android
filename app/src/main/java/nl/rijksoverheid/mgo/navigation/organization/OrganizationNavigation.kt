package nl.rijksoverheid.mgo.navigation.organization

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.overview.OverviewScreen
import nl.rijksoverheid.mgo.feature.healthcareprovider.removeprovider.RemoveProviderScreen
import nl.rijksoverheid.mgo.feature.organization.labResults.LabResultsScreen
import nl.rijksoverheid.mgo.feature.organization.medicationUse.MedicationUseScreen
import nl.rijksoverheid.mgo.feature.organization.organization.OrganizationScreen
import nl.rijksoverheid.mgo.feature.organization.problems.ProblemsScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.dialogWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen

@Composable
fun OrganizationNavigation(
    rootNavController: NavHostController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = OrganizationNavigationScreen.Overview.getRoute(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composableWithDefaultScreenTransitions(OrganizationNavigationScreen.Overview.getRoute()) {
            OverviewScreen(
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                },
                onNavigateToOrganization = { provider ->
                    navController.navigate(
                        OrganizationNavigationScreen.Organization
                            .setProvider(provider)
                            .getNavigationRoute(),
                    )
                },
            )
        }

        composableWithDefaultScreenTransitions(route = OrganizationNavigationScreen.Organization.getRoute()) { backStackEntry ->
            val provider = OrganizationNavigationScreen.Organization.getProvider(backStackEntry)
            OrganizationScreen(
                provider = provider,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToMedicationuse = {
                    navController.navigate(
                        OrganizationNavigationScreen.MedicationUse.setProvider(provider).getNavigationRoute(),
                    )
                },
                onNavigateToProblems = {
                    navController.navigate(OrganizationNavigationScreen.Problems.setProvider(provider).getNavigationRoute())
                },
                onNavigateToLabResults = {
                    navController.navigate(
                        OrganizationNavigationScreen.LabResults.setProvider(provider).getNavigationRoute(),
                    )
                },
                onNavigateToRemoveProvider = {
                    navController.navigate(
                        OrganizationNavigationScreen.RemoveProvider.setProviderId(provider.id).setProviderName
                            (provider.name).getNavigationRoute(),
                    )
                },
            )
        }

        dialogWithDefaultScreenTransitions(
            route = OrganizationNavigationScreen.RemoveProvider.getRoute(),
        ) { backStackEntry ->
            RemoveProviderScreen(
                providerId = OrganizationNavigationScreen.RemoveProvider.getProviderId(backStackEntry),
                providerName = OrganizationNavigationScreen.RemoveProvider.getProviderName(backStackEntry),
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDashboard = {
                    navController.popBackStack(
                        route = OrganizationNavigationScreen.Overview.getRoute(),
                        inclusive = false,
                    )
                },
            )
        }

        composableWithDefaultScreenTransitions(OrganizationNavigationScreen.MedicationUse.getRoute()) { backStackEntry ->
            MedicationUseScreen(
                provider = OrganizationNavigationScreen.MedicationUse.getProvider(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composableWithDefaultScreenTransitions(OrganizationNavigationScreen.Problems.getRoute()) { backStackEntry ->
            ProblemsScreen(
                provider = OrganizationNavigationScreen.Problems.getProvider(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composableWithDefaultScreenTransitions(OrganizationNavigationScreen.LabResults.getRoute()) { backStackEntry ->
            LabResultsScreen(
                provider = OrganizationNavigationScreen.LabResults.getProvider(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
