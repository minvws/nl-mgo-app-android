package nl.rijksoverheid.mgo.navigation.localisation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.toRoute
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.AddOrganizationScreen
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.AddOrganizationScreenViewModel
import nl.rijksoverheid.mgo.feature.localisation.manual.ManualLocalisationScreen
import nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic.OrganizationListAutomaticSearchScreen
import nl.rijksoverheid.mgo.feature.localisation.organizationList.manual.OrganizationListManualScreen
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.getViewModel
import nl.rijksoverheid.mgo.navigation.mgoComposableExt

fun NavGraphBuilder.addLocalisationNavGraph(navController: NavController) {
  navigation<LocalisationNavigation.Root>(LocalisationNavigation.Manual) {
    mgoComposableExt<LocalisationNavigation.Manual> {
      val onNavigateBack: (() -> Unit)? =
        if (navController.previousBackStackEntry == null) {
          null
        } else {
          { navController.popBackStack() }
        }
      ManualLocalisationScreen(
        onNavigateToDashboard = {
          // If coming from dashboard, we want to pop back
          val canPop =
            navController.popBackStack(
              route = LocalisationNavigation.Manual,
              inclusive = true,
            )
          // If not coming from dashboard, navigate to it
          if (!canPop) {
            navController.navigate(DashboardNavigation.Root) {
              popUpTo(navController.graph.id) {
                inclusive = true
              }
            }
          }
        },
        onNavigateBack = onNavigateBack,
      )
    }

    mgoComposableExt<LocalisationNavigation.AddOrganization> {
      val onNavigateBack: (() -> Unit)? =
        if (navController.previousBackStackEntry == null) {
          null
        } else {
          { navController.popBackStack() }
        }
      AddOrganizationScreen(
        onNavigateBack = onNavigateBack,
        onNavigateToOrganizationSearch = { name, city ->
          navController.navigate(LocalisationNavigation.OrganisationListManual(name = name, city = city))
        },
      )
    }

    mgoComposableExt<LocalisationNavigation.OrganisationListManual> { backStackEntry ->
      val route = backStackEntry.toRoute<LocalisationNavigation.OrganisationListManual>()
      val addOrganizationScreenViewModel =
        navController.getViewModel<AddOrganizationScreenViewModel>(
          route = LocalisationNavigation.AddOrganization,
        )
      OrganizationListManualScreen(
        name = route.name,
        city = route.city,
        onNavigateBack = { navController.popBackStack() },
        onNavigateToAddOrganization = {
          addOrganizationScreenViewModel?.setName("")
          addOrganizationScreenViewModel?.setCity("")
          navController.popBackStack(route = LocalisationNavigation.AddOrganization, inclusive = false)
        },
        onNavigateToDashboard = {
          // If coming from dashboard, we want to pop back
          val canPop =
            navController.popBackStack(
              route = LocalisationNavigation.AddOrganization,
              inclusive = true,
            )
          // If not coming from dashboard, navigate to it
          if (!canPop) {
            navController.navigate(DashboardNavigation.Root) {
              popUpTo(navController.graph.id) {
                inclusive = true
              }
            }
          }
        },
      )
    }

    mgoComposableExt<LocalisationNavigation.OrganizationListAutomatic> { backStackEntry ->
      val route = backStackEntry.toRoute<LocalisationNavigation.OrganizationListAutomatic>()
      val onNavigateBack: (() -> Unit)? =
        if (navController.previousBackStackEntry == null) {
          null
        } else {
          { navController.popBackStack() }
        }
      OrganizationListAutomaticSearchScreen(
        checkResults = route.checkResults,
        onNavigateBack = onNavigateBack,
        onNavigateToDashboard = {
          // If coming from dashboard, we want to pop back
          val canPop =
            navController.popBackStack(
              route = route,
              inclusive = true,
            )
          // If not coming from dashboard, navigate to it
          if (!canPop) {
            navController.navigate(DashboardNavigation.Root) {
              popUpTo(navController.graph.id) {
                inclusive = true
              }
            }
          }
        },
      )
    }
  }
}
