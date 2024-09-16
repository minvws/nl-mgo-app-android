package nl.rijksoverheid.mgo.navigation.organizations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.organizations.OrganizationsScreen
import nl.rijksoverheid.mgo.feature.dashboard.overview.HealthCategoriesScreenType
import nl.rijksoverheid.mgo.feature.dashboard.overview.OverviewScreen
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
                onNavigateToHealthCategories = { organization ->
                    navController.navigate(
                        OverviewNavigationScreen.Start.setScreenType(
                            HealthCategoriesScreenType.Single(organization),
                        ).getNavigationRoute(),
                    )
                },
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                },
            )
        }

        composableWithDefaultScreenTransitions(OverviewNavigationScreen.Start.getRoute()) { backStackEntry ->
            OverviewScreen(
                screenType = OverviewNavigationScreen.Start.getScreenType(backStackEntry),
                onNavigateToLocalisation = {
                    rootNavController.navigate(OverviewNavigationScreen.Start.getNavigationRoute())
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHealthCategory = {
                    navController.navigate(OverviewNavigationScreen.HealthCategory.getNavigationRoute())
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
