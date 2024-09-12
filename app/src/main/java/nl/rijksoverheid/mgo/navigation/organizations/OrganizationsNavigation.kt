package nl.rijksoverheid.mgo.navigation.organizations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.organizations.OrganizationsScreen
import nl.rijksoverheid.mgo.feature.organization.removeOrganization.RemoveOrganizationScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.dialogWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen
import nl.rijksoverheid.mgo.navigation.organization.OverviewNavigationScreen

@Composable
fun OrganizationsNavigation(
    rootNavController: NavHostController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = OrganizationsNavigationScreen.Start.getRoute(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composableWithDefaultScreenTransitions(OrganizationsNavigationScreen.Start.getRoute()) {
            OrganizationsScreen(
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                },
            )
        }

        dialogWithDefaultScreenTransitions(
            route = OrganizationsNavigationScreen.RemoveOverview.getRoute(),
        ) { backStackEntry ->
            RemoveOrganizationScreen(
                providerId = OrganizationsNavigationScreen.RemoveOverview.getProviderId(backStackEntry),
                providerName = OrganizationsNavigationScreen.RemoveOverview.getProviderName(backStackEntry),
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDashboard = {
                    navController.popBackStack(
                        route = OverviewNavigationScreen.Start.getRoute(),
                        inclusive = false,
                    )
                },
            )
        }
    }
}
