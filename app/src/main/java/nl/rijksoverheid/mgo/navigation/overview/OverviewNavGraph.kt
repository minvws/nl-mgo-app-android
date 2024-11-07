package nl.rijksoverheid.mgo.navigation.overview

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.BottomBarNavigation
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenArguments
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.UiSchemaDetailScreen
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen
import nl.rijksoverheid.mgo.navigation.newComposableWithDefaultScreenTransitions
import timber.log.Timber

fun NavGraphBuilder.addOverviewNavGraph(
    rootNavController: NavController,
    navController: NavController
) {
    composable<BottomBarNavigation.Overview> {
        HealthCategoriesScreen(
            appBarTitle = stringResource(R.string.overview_heading),
            onNavigateToLocalisation = {
                rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
            },
            onNavigateToHealthCategory = { category, _ ->
                navController.navigate(
                    OverviewNavigation.HealthCategory(HealthCategoryScreenArguments(category = category, filterOrganization = null)),
                )
            },
            onNavigateRemoveOrganization = { },
        )
    }

    newComposableWithDefaultScreenTransitions<OverviewNavigation.HealthCategory> { backStackEntry ->
        val route = backStackEntry.toRoute<OverviewNavigation.HealthCategory>()
        HealthCategoryScreen(
            arguments = route.arguments,
            onClickUiSchema = { toolbarTitle, uiSchema ->
                navController.navigate(OverviewNavigation.UiSchemaDetail(toolbarTitle = toolbarTitle, uiSchema = uiSchema))
            },
            onNavigateBack = {
                navController.popBackStack()
            },
        )
    }

    newComposableWithDefaultScreenTransitions<OverviewNavigation.UiSchemaDetail> { backStackEntry ->
        val route = backStackEntry.toRoute<OverviewNavigation.UiSchemaDetail>()
        UiSchemaDetailScreen(
            toolbarTitle = route.toolbarTitle,
            uiSchema = route.uiSchema,
            onNavigateBack = {
                navController.popBackStack()
            },
        )
    }
}
