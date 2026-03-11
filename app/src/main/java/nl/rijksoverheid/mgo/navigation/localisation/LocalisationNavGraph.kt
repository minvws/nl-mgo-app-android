package nl.rijksoverheid.mgo.navigation.localisation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.localisation.manual.ManualLocalisationScreen
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
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
  }
}
