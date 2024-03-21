package nl.rijksoverheid.mgo.framework.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalNavigationManager = compositionLocalOf<NavigationManager> { EmptyNavigationManager() }

@Composable
fun ProvideNavigationManager(
    navigationManager: NavigationManager = EmptyNavigationManager(),
    block: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalNavigationManager provides navigationManager,
    ) {
        block()
    }
}
