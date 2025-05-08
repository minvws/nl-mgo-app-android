package nl.rijksoverheid.mgo.navigation.digid

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.feature.digid.DigidLoginScreen
import nl.rijksoverheid.mgo.feature.digid.DigidMockScreen
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_DIGID_AUTHENTICATED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.mgoComposable

/**
 * Adds all the navigation destinations that can be found when logging in with DigiD.
 * @param navController The nav controller used in this navigation.
 * @param keyValueStore The store to save if a user has been authenticated before with DigiD (temporary).
 */
fun NavGraphBuilder.addDigidNavGraph(
  navController: NavController,
  keyValueStore: KeyValueStore,
) {
  navigation<DigidNavigation.Root>(DigidNavigation.Login) {
    mgoComposable<DigidNavigation.Login> {
      DigidLoginScreen(
        onNavigateToDigidMock = {
          navController.navigate(DigidNavigation.Mock)
        },
      )
    }

    mgoComposable<DigidNavigation.Mock> {
      DigidMockScreen(
        onNavigateToLocalisation = {
          // Temporary do this here so we do not have to create an test a whole viewmodel for a temporary mock screen.
          // This would normally go in a viewmodel after the real DigiD flow has been authenticated
          runBlocking { keyValueStore.setBoolean(KEY_DIGID_AUTHENTICATED, true) }

          navController.navigate(LocalisationNavigation.Root(true)) {
            popUpTo(navController.graph.id) {
              inclusive = true
            }
          }
        },
      )
    }
  }
}
