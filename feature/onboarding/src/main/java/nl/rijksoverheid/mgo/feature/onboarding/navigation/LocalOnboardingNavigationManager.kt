package nl.rijksoverheid.mgo.feature.onboarding.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

internal val LocalOnboardingNavigationManager =
    compositionLocalOf<NavigationManager<OnboardingNavigationScreen>> {
        EmptyOnboardingNavigationManager()
    }

@Composable
internal fun ProvideOnboardingNavigationManager(
    navigationManager: NavigationManager<OnboardingNavigationScreen> = EmptyOnboardingNavigationManager(),
    block: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalOnboardingNavigationManager provides navigationManager,
    ) {
        block()
    }
}
