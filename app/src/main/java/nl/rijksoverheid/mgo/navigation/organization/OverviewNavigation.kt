package nl.rijksoverheid.mgo.navigation.organization

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreenArguments
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenArguments
import nl.rijksoverheid.mgo.feature.uiSchemaDetail.UiSchemaDetailScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigationScreen
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen

@Composable
fun OverviewNavigation(
    rootNavController: NavHostController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination =
            DashboardNavigationScreen.HealthCategories.setArguments(
                HealthCategoriesScreenArguments(filterOrganization = null),
            ).getNavigationRoute(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composableWithDefaultScreenTransitions(DashboardNavigationScreen.HealthCategories.getRoute()) { backStackEntry ->
            HealthCategoriesScreen(
                arguments = DashboardNavigationScreen.HealthCategories.getArguments(backStackEntry),
                onNavigateBack = {},
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                },
                onNavigateToHealthCategory = {
                    navController.navigate(
                        DashboardNavigationScreen.HealthCategory.setArguments(
                            HealthCategoryScreenArguments(filterOrganization = null),
                        ).getNavigationRoute(),
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
    }
}
