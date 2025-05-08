package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaScreen
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposable

/**
 * Adds all the navigation destinations that can be found in the overview bottom bar tab in the dashboard.
 *
 * @param rootNavController The top level nav controller.
 * @param navController The nav controller used in this navigation.
 */
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
        onClickListItem = { organization, uiSchema ->
          navController.navigate(
            DashboardNavigation.Overview.UiSchema(
              organization = organization,
              mgoResource = uiSchema,
              isSummary = true,
            ),
          )
        },
        onNavigateBack = {
          navController.popBackStack()
        },
      )
    }

    mgoComposable<DashboardNavigation.Overview.UiSchema> { backStackEntry ->
      val route = backStackEntry.toRoute<DashboardNavigation.Overview.UiSchema>()
      UiSchemaScreen(
        organization = route.organization,
        mgoResource = route.mgoResource,
        isSummary = route.isSummary,
        onNavigateToUiSchema = { organization, mgoResource ->
          val uiSchema =
            DashboardNavigation.Overview.UiSchema(
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
  }
}
