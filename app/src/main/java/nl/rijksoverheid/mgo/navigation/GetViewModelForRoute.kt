package nl.rijksoverheid.mgo.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation.AddOrganization
import nl.rijksoverheid.mgo.navigation.localisation.LocalisationNavigation.OrganizationListAutomatic

/**
 * Helper function to get a viewmodel from a route. For example if you are in
 * [LocalisationNavigation] in screen [OrganizationListAutomatic] and want to get the viewmodel created in
 * [AddOrganization], you can use this function to do so.
 * @param route The name of the route you want to get the viewmodel from.
 */
@Composable
inline fun <reified VM : ViewModel> NavController.getViewModel(route: Any): VM? {
  val entry =
    try {
      getBackStackEntry(route)
    } catch (e: Exception) {
      null
    }
  if (entry == null) {
    return null
  }
  val viewModel: VM = hiltViewModel(viewModelStoreOwner = entry)
  return viewModel
}
