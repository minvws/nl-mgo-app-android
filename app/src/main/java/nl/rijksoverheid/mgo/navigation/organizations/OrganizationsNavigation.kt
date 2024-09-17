package nl.rijksoverheid.mgo.navigation.organizations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreenType
import nl.rijksoverheid.mgo.feature.dashboard.organizations.OrganizationsScreen
import nl.rijksoverheid.mgo.feature.organization.removeOrganization.RemoveOrganizationScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigationScreen
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
                        DashboardNavigationScreen.HealthCategories.setScreenType(
                            HealthCategoriesScreenType.Single(organization),
                        ).getNavigationRoute(),
                    )
                },
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                },
            )
        }

        composableWithDefaultScreenTransitions(DashboardNavigationScreen.HealthCategories.getRoute()) { backStackEntry ->
            HealthCategoriesScreen(
                screenType = DashboardNavigationScreen.HealthCategories.getScreenType(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
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
                        route = OrganizationsNavigationScreen.Start.getRoute(),
                        inclusive = false,
                    )
                },
            )
        }
    }
}
