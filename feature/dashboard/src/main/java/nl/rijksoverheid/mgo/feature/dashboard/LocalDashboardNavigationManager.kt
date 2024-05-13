package nl.rijksoverheid.mgo.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import nl.rijksoverheid.mgo.framework.navigation.EmptyNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

val LocalDashboardNavigationManager = compositionLocalOf<NavigationManager<*>> { EmptyDashboardNavigationManager() }

@Composable
fun ProvideDashboardNavigationManager(
    navigationManager: NavigationManager<*> = EmptyNavigationManager(),
    block: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalDashboardNavigationManager provides navigationManager,
    ) {
        block()
    }
}
