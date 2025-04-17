package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.organizations.OrganizationsScreen
import nl.rijksoverheid.mgo.feature.dashboard.removeOrganization.RemoveOrganizationScreen
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaScreen
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposable
import nl.rijksoverheid.mgo.navigation.mgoComposableDialog

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
    mgoComposable<DashboardNavigation.Organizations.List>(animate = false) {
      OrganizationsScreen(
        onNavigateToHealthCategories = { organization ->
          navController.navigate(DashboardNavigation.Organizations.HealthCareCategories(organization))
        },
        onNavigateToLocalisation = {
          rootNavController.navigate(LocalisationNavigation.Root(false))
        },
      )
    }

    mgoComposable<DashboardNavigation.Organizations.HealthCareCategories> { backStackEntry ->
      val route = backStackEntry.toRoute<DashboardNavigation.Organizations.HealthCareCategories>()
      HealthCategoriesScreen(
        appBarTitle = route.organization.name,
        subHeading = stringResource(R.string.overview_organizations_subheading),
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
        onNavigateBack = { navController.popBackStack() },
      )
    }

    mgoComposable<DashboardNavigation.Organizations.HealthCareCategory> { backStackEntry ->
      val route = backStackEntry.toRoute<DashboardNavigation.Organizations.HealthCareCategory>()
      HealthCategoryScreen(
        category = route.category,
        filterOrganization = route.filterOrganization,
        onClickListItem = { organization, mgoResource ->
          navController.navigate(
            DashboardNavigation.Organizations.UiSchema(
              organization = organization,
              mgoResource = mgoResource,
              isSummary = true,
            ),
          )
        },
        onNavigateBack = {
          navController.popBackStack()
        },
      )
    }

    mgoComposable<DashboardNavigation.Organizations.UiSchema> { backStackEntry ->
      val route = backStackEntry.toRoute<DashboardNavigation.Organizations.UiSchema>()
      UiSchemaScreen(
        organization = route.organization,
        mgoResource = route.mgoResource,
        isSummary = route.isSummary,
        onNavigateToUiSchema = { organization, mgoResource ->
          val uiSchema =
            DashboardNavigation.Organizations.UiSchema(
              organization = organization,
              mgoResource = mgoResource,
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
