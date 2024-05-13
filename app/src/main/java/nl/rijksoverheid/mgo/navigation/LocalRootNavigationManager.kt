package nl.rijksoverheid.mgo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

val LocalRootNavigationManager = compositionLocalOf<NavigationManager<RootNavigationScreen>> { EmptyRootNavigationManager() }

@Composable
fun ProvideNavigationManager(
    navigationManager: NavigationManager<RootNavigationScreen> = EmptyRootNavigationManager(),
    block: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRootNavigationManager provides navigationManager,
    ) {
        block()
    }
}
