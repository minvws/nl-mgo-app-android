package nl.rijksoverheid.mgo.feature.localisation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal val LocalLocalisationNavigationManager =
    compositionLocalOf<NavigationManager<LocalisationNavigationScreen>> {
        EmptyLocalisationNavigationManager()
    }

@Composable
internal fun ProvideLocalisationNavigationManager(
    navigationManager: NavigationManager<LocalisationNavigationScreen> = EmptyLocalisationNavigationManager(),
    block: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalLocalisationNavigationManager provides navigationManager,
    ) {
        block()
    }
}
