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
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenArguments
import timber.log.Timber
import kotlin.reflect.KType
import kotlin.reflect.typeOf

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

inline fun <reified T : Any> NavGraphBuilder.newComposableWithDefaultScreenTransitions(
    deepLinks: List<NavDeepLink> = emptyList(),
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable<T>(
    typeMap =
        mapOf(
            typeOf<MgoOrganization?>() to JsonNavType(MgoOrganization::class.java, MgoOrganization.serializer()),
            typeOf<HealthCategoryScreenArguments>() to
                JsonNavType(
                    HealthCategoryScreenArguments::class.java,
                    HealthCategoryScreenArguments.serializer(),
                ),
            typeOf<UISchema>() to JsonNavType(UISchema::class.java, UISchema.serializer()),
        ),
    deepLinks = deepLinks,
    enterTransition = {
        if (isSameBottomBarItem()) {
            defaultScreenEnterTransition()
        } else {
            null
        }
      },
    exitTransition = { defaultScreenExitTransition() },
    popEnterTransition = { defaultScreenPopEnterTransition() },
    content = content,
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.isSameBottomBarItem(): Boolean {
    val startDestinationRoute = initialState.destination.parent?.startDestinationRoute
    val destinationRoute = initialState.destination.route
    return startDestinationRoute == destinationRoute
}

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
