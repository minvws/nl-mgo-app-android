package nl.rijksoverheid.mgo.navigation.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.feature.dashboard.editOverview.EditOverviewBottomSheet
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreen
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreen
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaScreen
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposableExt

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
    mgoComposableExt<DashboardNavigation.Overview.HealthCareCategories>(animate = false) { backStackEntry ->
      // Edit overview bottomsheet
      var showBottomSheet by remember { mutableStateOf(false) }
      if (showBottomSheet) {
        EditOverviewBottomSheet {
          showBottomSheet = false
        }
      }

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
        onShowBottomSheet = {
          showBottomSheet = true
        },
      )
    }

    mgoComposableExt<DashboardNavigation.Overview.HealthCareCategory> { backStackEntry ->
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

    mgoComposableExt<DashboardNavigation.Overview.UiSchema> { backStackEntry ->
      val route = backStackEntry.toRoute<DashboardNavigation.Overview.UiSchema>()
      UiSchemaScreen(
        organization = route.organization,
        mgoResource = route.mgoResource,
        isSummary = route.isSummary,
        onNavigateToDetail = { organization, mgoResource ->
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
