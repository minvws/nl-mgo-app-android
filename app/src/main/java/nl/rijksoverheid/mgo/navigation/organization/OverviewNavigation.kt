package nl.rijksoverheid.mgo.navigation.organization

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import nl.rijksoverheid.mgo.feature.dashboard.overview.HealthCategoriesScreenType
import nl.rijksoverheid.mgo.feature.dashboard.overview.OverviewScreen
import nl.rijksoverheid.mgo.feature.organization.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.uiSchemaDetail.UiSchemaDetailScreen
import nl.rijksoverheid.mgo.navigation.composableWithDefaultScreenTransitions

@Composable
fun OverviewNavigation(
    rootNavController: NavHostController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = OverviewNavigationScreen.Start.setScreenType(HealthCategoriesScreenType.All()).getNavigationRoute(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composableWithDefaultScreenTransitions(OverviewNavigationScreen.Start.getRoute()) { backStackEntry ->
            OverviewScreen(
                screenType = OverviewNavigationScreen.Start.getScreenType(backStackEntry),
                onNavigateBack = {},
                onNavigateToLocalisation = {
                    rootNavController.navigate(OverviewNavigationScreen.Start.getNavigationRoute())
                },
                onNavigateToHealthCategory = {
                    navController.navigate(OverviewNavigationScreen.HealthCategory.getNavigationRoute())
                },
            )
        }

        composableWithDefaultScreenTransitions(OverviewNavigationScreen.HealthCategory.getRoute()) {
            HealthCategoryScreen(
                onClickUiSchema = { toolbarTitle, uiSchema ->
                    navController.navigate(
                        OverviewNavigationScreen.UiSchemaDetail.setToolbarTitle(toolbarTitle).setUiSchema(uiSchema)
                            .getNavigationRoute(),
                    )
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composableWithDefaultScreenTransitions(OverviewNavigationScreen.UiSchemaDetail.getRoute()) { backStackEntry ->
            UiSchemaDetailScreen(
                toolbarTitle = OverviewNavigationScreen.UiSchemaDetail.getToolbarTitle(backStackEntry),
                uiSchema = OverviewNavigationScreen.UiSchemaDetail.getUiSchema(backStackEntry),
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
