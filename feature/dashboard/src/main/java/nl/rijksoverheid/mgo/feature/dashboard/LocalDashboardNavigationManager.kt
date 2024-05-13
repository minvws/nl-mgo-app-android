package nl.rijksoverheid.mgo.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import nl.rijksoverheid.mgo.feature.dashboard.overview.navigation.DashboardNavigationScreen
import nl.rijksoverheid.mgo.feature.dashboard.overview.navigation.EmptyDashboardNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

val LocalDashboardNavigationManager = compositionLocalOf<NavigationManager<DashboardNavigationScreen>> { EmptyDashboardNavigationManager() }

@Composable
fun ProvideDashboardNavigationManager(
    navigationManager: NavigationManager<DashboardNavigationScreen> = EmptyDashboardNavigationManager(),
    block: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalDashboardNavigationManager provides navigationManager,
    ) {
        block()
    }
}
