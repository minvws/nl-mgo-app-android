package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.organizations.OrganizationsScreen
import nl.rijksoverheid.mgo.feature.dashboard.removeOrganization.RemoveOrganizationScreen
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.UiSchemaDetailScreen
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposable
import nl.rijksoverheid.mgo.navigation.mgoComposableDialog

fun NavGraphBuilder.addDashboardOrganizationsNavGraph(
    rootNavController: NavController,
    navController: NavController,
) {
    navigation<DashboardNavigation.Organizations.Root>(DashboardNavigation.Organizations.List) {
        mgoComposable<DashboardNavigation.Organizations.List>(animate = false) {
            OrganizationsScreen(
                onNavigateToHealthCategories = { organization ->
                    navController.navigate(DashboardNavigation.Organizations.HealthCareCategories(organization))
                },
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigation.Root)
                },
            )
        }

        mgoComposable<DashboardNavigation.Organizations.HealthCareCategories> { backStackEntry ->
            val route = backStackEntry.toRoute<DashboardNavigation.Organizations.HealthCareCategories>()
            HealthCategoriesScreen(
                appBarTitle = route.organization.name,
                organization = route.organization,
                onNavigateToLocalisation = {
                    rootNavController.navigate(LocalisationNavigation.Root)
                },
                onNavigateToHealthCategory = { category, filterOrganization ->
                    navController.navigate(
                        DashboardNavigation.Organizations.HealthCareCategory(
                            category = category,
                            filterOrganization = filterOrganization!!,
                        ),
                    )
                },
                onNavigateRemoveOrganization = { organization ->
                    navController.navigate(
                        DashboardNavigation.Organizations
                            .RemoveOrganization(organizationId = organization.id, organizationName = organization.name),
                    )
                },
                onNavigateBack = { navController.popBackStack() },
            )
        }

        mgoComposable<DashboardNavigation.Organizations.HealthCareCategory> { backStackEntry ->
            val route = backStackEntry.toRoute<DashboardNavigation.Organizations.HealthCareCategory>()
            HealthCategoryScreen(
                category = route.category,
                filterOrganization = route.filterOrganization,
                onClickUiSchema = { toolbarTitle, uiSchema ->
                    navController.navigate(
                        DashboardNavigation.Organizations.UISchemaDetail(
                            toolbarTitle = toolbarTitle,
                            uiSchema = uiSchema,
                        ),
                    )
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        mgoComposable<DashboardNavigation.Organizations.UISchemaDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<DashboardNavigation.Organizations.UISchemaDetail>()
            UiSchemaDetailScreen(
                toolbarTitle = route.toolbarTitle,
                uiSchema = route.uiSchema,
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        mgoComposableDialog<DashboardNavigation.Organizations.RemoveOrganization> { backStackEntry ->
            val route = backStackEntry.toRoute<DashboardNavigation.Organizations.RemoveOrganization>()
            RemoveOrganizationScreen(
                providerId = route.organizationId,
                providerName = route.organizationName,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDashboard = {
                    navController.popBackStack(
                        route = DashboardNavigation.Organizations.List,
                        inclusive = false,
                    )
                },
            )
        }
    }
}
