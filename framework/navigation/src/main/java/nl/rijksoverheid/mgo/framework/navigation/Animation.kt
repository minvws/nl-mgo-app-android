package nl.rijksoverheid.mgo.framework.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SCREEN_TRANSITION_DURATION_MILLIS = 250

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
