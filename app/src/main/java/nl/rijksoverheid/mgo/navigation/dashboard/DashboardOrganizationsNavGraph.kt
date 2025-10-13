package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.component.mgo.navigation.mgoComposableDialog
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.organizations.OrganizationsScreen
import nl.rijksoverheid.mgo.feature.dashboard.removeOrganization.RemoveOrganizationScreen
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaScreen
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposableExt

/**
 * Adds all the navigation destinations that can be found in the organizations bottom bar tab in the dashboard.
 *
 * @param rootNavController The top level nav controller.
 * @param navController The nav controller used in this navigation.
 */
fun NavGraphBuilder.addDashboardOrganizationsNavGraph(
  rootNavController: NavController,
  navController: NavController,
) {
  navigation<DashboardNavigation.Organizations.Root>(DashboardNavigation.Organizations.List) {
    mgoComposableExt<DashboardNavigation.Organizations.List>(animate = false) {
      OrganizationsScreen(
        onNavigateToHealthCategories = { organization ->
          navController.navigate(DashboardNavigation.Organizations.HealthCareCategories(organization))
        },
        onNavigateToLocalisation = {
          rootNavController.navigate(LocalisationNavigation.Root(false))
        },
      )
    }

    mgoComposableExt<DashboardNavigation.Organizations.HealthCareCategories> { backStackEntry ->
      val route = backStackEntry.toRoute<DashboardNavigation.Organizations.HealthCareCategories>()
      HealthCategoriesScreen(
        appBarTitle = route.organization.name,
        organization = route.organization,
        onNavigateToLocalisation = {
          rootNavController.navigate(LocalisationNavigation.Root(false))
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
        onShowBottomSheet = null,
        onNavigateBack = { navController.popBackStack() },
      )
    }

    mgoComposableExt<DashboardNavigation.Organizations.HealthCareCategory> { backStackEntry ->
      val route = backStackEntry.toRoute<DashboardNavigation.Organizations.HealthCareCategory>()
      HealthCategoryScreen(
        category = route.category,
        filterOrganization = route.filterOrganization,
        onClickListItem = { organization, referenceId ->
          navController.navigate(
            DashboardNavigation.Organizations.UiSchema(
              organization = organization,
              referenceId = referenceId,
              isSummary = true,
            ),
          )
        },
        onNavigateBack = {
          navController.popBackStack()
        },
      )
    }

    mgoComposableExt<DashboardNavigation.Organizations.UiSchema> { backStackEntry ->
      val route = backStackEntry.toRoute<DashboardNavigation.Organizations.UiSchema>()
      UiSchemaScreen(
        organization = route.organization,
        referenceId = route.referenceId,
        isSummary = route.isSummary,
        onNavigateToDetail = { organization, referenceId ->
          val uiSchema =
            DashboardNavigation.Organizations.UiSchema(
              organization = organization,
              referenceId = referenceId,
              isSummary = false,
            )
          navController.navigate(uiSchema)
        },
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
