package nl.rijksoverheid.mgo.navigation.digid

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import nl.rijksoverheid.mgo.feature.digid.DigidLoginScreen
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposableExt

fun NavGraphBuilder.addDigidNavGraph(navController: NavController) {
  navigation<DigidNavigation.Root>(DigidNavigation.Login) {
    mgoComposableExt<DigidNavigation.Login> {
      DigidLoginScreen(
        onFinishedLogin = {
          navController.navigate(LocalisationNavigation.Root) {
            popUpTo(navController.graph.id) {
              inclusive = true
            }
          }
        },
      )
    }
  }
}
