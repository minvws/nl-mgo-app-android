package nl.rijksoverheid.mgo.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

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
