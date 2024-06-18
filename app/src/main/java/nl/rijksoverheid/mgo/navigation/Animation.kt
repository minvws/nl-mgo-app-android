package nl.rijksoverheid.mgo.navigation

import android.view.ViewGroup
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog

const val SCREEN_TRANSITION_DURATION_MILLIS = 250

fun NavGraphBuilder.dialogWithDefaultScreenTransitions(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    dialog(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        content = { backStackEntry ->
            // Make dialog full screen
            val window = (LocalView.current.parent as DialogWindowProvider).window
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            // Disable scrim
            window.setDimAmount(0f)

            // Show content
            content(backStackEntry)
        },
    )
}

fun NavGraphBuilder.composableWithDefaultScreenTransitions(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable(
    route = route,
    arguments = arguments,
    deepLinks = deepLinks,
    enterTransition = { defaultScreenEnterTransition() },
    exitTransition = { defaultScreenExitTransition() },
    popEnterTransition = { defaultScreenPopEnterTransition() },
    content = content,
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.defaultScreenEnterTransition() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(SCREEN_TRANSITION_DURATION_MILLIS),
    ) + fadeIn(animationSpec = tween(SCREEN_TRANSITION_DURATION_MILLIS))

/**
 * The animation that plays when exiting a screen.
 */
fun defaultScreenExitTransition() =
    scaleOut(
        animationSpec = tween(SCREEN_TRANSITION_DURATION_MILLIS),
        targetScale = 0.95f,
    ) + fadeOut(animationSpec = tween(SCREEN_TRANSITION_DURATION_MILLIS))

/**
 * The animation on the screen that you are going back to.
 */
fun defaultScreenPopEnterTransition() =
    scaleIn(
        animationSpec = tween(SCREEN_TRANSITION_DURATION_MILLIS),
        initialScale = 0.95f,
    ) + fadeIn(animationSpec = tween(SCREEN_TRANSITION_DURATION_MILLIS))
