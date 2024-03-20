package nl.rijksoverheid.mgo.framework.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavBackStackEntry

const val SCREEN_TRANSITION_DURATION_MILLIS = 250

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
