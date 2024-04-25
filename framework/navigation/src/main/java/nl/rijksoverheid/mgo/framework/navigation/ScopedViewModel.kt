package nl.rijksoverheid.mgo.framework.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel

@Composable
inline fun <reified VM : ViewModel> scopedViewModel(navigationScreen: NavigationScreen): VM {
    val navigationManager = LocalNavigationManager.current
    val parentEntry =
        remember(navigationScreen) {
            requireNotNull(navigationManager.getBackStackEntry(navigationScreen))
        }
    return hiltViewModel<VM>(parentEntry)
}
