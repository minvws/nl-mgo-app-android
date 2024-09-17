package nl.rijksoverheid.mgo.navigation.organizations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreenArguments
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenArguments
import nl.rijksoverheid.mgo.feature.dashboard.organizations.OrganizationsScreen
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.UiSchemaDetailScreen
import nl.rijksoverheid.mgo.feature.organization.removeOrganization.RemoveOrganizationScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigationScreen
import nl.rijksoverheid.mgo.navigation.dialogWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen

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
                        DashboardNavigationScreen.HealthCategories.setArguments(
                            HealthCategoriesScreenArguments(filterOrganization = organization),
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
                arguments = DashboardNavigationScreen.HealthCategories.getArguments(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                },
                onNavigateToHealthCategory = { organization ->
                    navController.navigate(
                        DashboardNavigationScreen.HealthCategory.setArguments(HealthCategoryScreenArguments(organization))
                            .getNavigationRoute(),
                    )
                },
            )
        }

        composableWithDefaultScreenTransitions(DashboardNavigationScreen.HealthCategory.getRoute()) { backStackEntry ->
            HealthCategoryScreen(
                arguments = DashboardNavigationScreen.HealthCategory.getArguments(backStackEntry),
                onClickUiSchema = { toolbarTitle, uiSchema ->
                    navController.navigate(
                        DashboardNavigationScreen.UiSchemaDetail.setToolbarTitle(toolbarTitle).setUiSchema(uiSchema)
                            .getNavigationRoute(),
                    )
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composableWithDefaultScreenTransitions(DashboardNavigationScreen.UiSchemaDetail.getRoute()) { backStackEntry ->
            UiSchemaDetailScreen(
                toolbarTitle = DashboardNavigationScreen.UiSchemaDetail.getToolbarTitle(backStackEntry),
                uiSchema = DashboardNavigationScreen.UiSchemaDetail.getUiSchema(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
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
