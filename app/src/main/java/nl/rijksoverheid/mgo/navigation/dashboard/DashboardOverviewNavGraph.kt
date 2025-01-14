package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.UiSchemaDetailScreen
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposable

fun NavGraphBuilder.addDashboardOverviewNavGraph(
    rootNavController: NavController,
    navController: NavController,
) {
    navigation<DashboardNavigation.Overview.Root>(DashboardNavigation.Overview.HealthCareCategories) {
        mgoComposable<DashboardNavigation.Overview.HealthCareCategories>(animate = false) {
            HealthCategoriesScreen(
                appBarTitle = stringResource(R.string.overview_heading),
                subHeading = stringResource(R.string.overview_subheading),
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigation.Root(false))
                },
                onNavigateToHealthCategory = { category, _ ->
                    navController.navigate(
                        DashboardNavigation.Overview.HealthCareCategory(category = category),
                    )
                },
                onNavigateRemoveOrganization = { },
            )
        }

        mgoComposable<DashboardNavigation.Overview.HealthCareCategory> { backStackEntry ->
            val route = backStackEntry.toRoute<DashboardNavigation.Overview.HealthCareCategory>()
            HealthCategoryScreen(
                category = route.category,
                onClickListItem = { toolbarTitle, organization, uiSchema ->
                    navController.navigate(
                        DashboardNavigation.Overview.UISchemaDetail(
                            toolbarTitle = toolbarTitle,
                            organization = organization,
                            mgoResource = uiSchema,
                        ),
                    )
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        mgoComposable<DashboardNavigation.Overview.UISchemaDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<DashboardNavigation.Overview.UISchemaDetail>()
            UiSchemaDetailScreen(
                toolbarTitle = route.toolbarTitle,
                organization = route.organization,
                mgoResource = route.mgoResource,
                isSummary = true,
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
