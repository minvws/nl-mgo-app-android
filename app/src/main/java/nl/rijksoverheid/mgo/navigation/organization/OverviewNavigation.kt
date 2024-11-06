package nl.rijksoverheid.mgo.navigation.organization

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenArguments
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.UiSchemaDetailScreen
import nl.rijksoverheid.mgo.navigation.CustomNavType
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigationScreen
import nl.rijksoverheid.mgo.navigation.newComposableWithDefaultScreenTransitions
import kotlin.reflect.typeOf
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun OverviewNavigation(
    rootNavController: NavHostController,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = DashboardNavigation.HealthCategories(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        newComposableWithDefaultScreenTransitions<DashboardNavigation.HealthCategories> {
            HealthCategoriesScreen(
                appBarTitle = stringResource(CopyR.string.overview_heading),
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigationScreen.Start.getNavigationRoute())
                },
                onNavigateToHealthCategory = { category, _ ->
                    navController.navigate(
                        DashboardNavigation.HealthCategory(HealthCategoryScreenArguments(category = category, filterOrganization = null))
                    )
                },
                onNavigateRemoveOrganization = { },
            )
        }

        newComposableWithDefaultScreenTransitions<DashboardNavigation.HealthCategory> { backStackEntry ->
            val route = backStackEntry.toRoute<DashboardNavigation.HealthCategory>()
            HealthCategoryScreen(
                arguments = route.arguments,
                onClickUiSchema = { toolbarTitle, uiSchema ->
                    navController.navigate(DashboardNavigation.UiSchemaDetail(toolbarTitle = toolbarTitle, uiSchema = uiSchema))
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        newComposableWithDefaultScreenTransitions<DashboardNavigation.UiSchemaDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<DashboardNavigation.UiSchemaDetail>()
//            UiSchemaDetailScreen(
//                toolbarTitle = route.toolbarTitle,
//                uiSchema = route.uiSchema,
//                onNavigateBack = {
//                    navController.popBackStack()
//                },
//            )
        }
    }
}
