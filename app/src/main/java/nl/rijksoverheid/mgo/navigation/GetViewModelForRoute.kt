package nl.rijksoverheid.mgo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
inline fun <reified VM : ViewModel> NavController.getViewModel(route: Any): VM? {
  val currentEntry = currentBackStackEntryAsState().value
  val entry =
    remember(currentEntry) {
      runCatching { getBackStackEntry(route) }.getOrNull()
    } ?: return null

  return hiltViewModel(viewModelStoreOwner = entry)
}
