package nl.rijksoverheid.mgo.navigation.digid

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.digid.DigidLoginScreen
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposableExt

fun NavGraphBuilder.addDigidNavGraph(
  navController: NavController,
  fromOnboarding: Boolean,
) {
  navigation<DigidNavigation.Root>(DigidNavigation.Login) {
    mgoComposableExt<DigidNavigation.Login> { backStackEntry ->
      val onNavigateBack: (() -> Unit)? =
        if (navController.previousBackStackEntry == null) {
          null
        } else {
          { navController.popBackStack() }
        }

      DigidLoginScreen(
        onNavigateBack = onNavigateBack,
        fromOnboarding = fromOnboarding,
        onFinishedLogin = {
          val navigation = if (fromOnboarding) LocalisationNavigation.Root else DashboardNavigation.Root
          navController.navigate(navigation) {
            popUpTo(navController.graph.id) {
              inclusive = true
            }
          }
        },
      )
    }
  }
}
