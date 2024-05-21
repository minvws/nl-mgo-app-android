package nl.rijksoverheid.mgo.feature.dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal val LocalDashboardNavigationManager =
    compositionLocalOf<NavigationManager<DashboardNavigationScreen>> {
        EmptyDashboardNavigationManager()
    }

@Composable
internal fun ProvideDashboardNavigationManager(
    navigationManager: NavigationManager<DashboardNavigationScreen> = EmptyDashboardNavigationManager(),
    block: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalDashboardNavigationManager provides navigationManager,
    ) {
        block()
    }
}
