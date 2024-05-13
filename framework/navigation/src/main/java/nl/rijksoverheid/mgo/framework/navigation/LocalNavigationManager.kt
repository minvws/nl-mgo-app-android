package nl.rijksoverheid.mgo.framework.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalNavigationManager = compositionLocalOf<NavigationManager<NavigationScreen>> { EmptyNavigationManager() }

@Composable
fun ProvideNavigationManager(
    navigationManager: NavigationManager<NavigationScreen> = EmptyNavigationManager(),
    block: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalNavigationManager provides navigationManager,
    ) {
        block()
    }
}
