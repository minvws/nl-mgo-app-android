package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.snackbar.LocalSnackBarPresenter
import nl.rijksoverheid.mgo.component.mgo.snackbar.MgoSnackBar
import nl.rijksoverheid.mgo.component.mgo.snackbar.MgoSnackBarVisuals
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.MgoTypography
import nl.rijksoverheid.mgo.component.theme.symbolsPrimary
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

sealed class MgoScaffoldScrollStateProvider {
    data object None : MgoScaffoldScrollStateProvider()

    data class Column(val scrollState: ScrollState) : MgoScaffoldScrollStateProvider()

    data class LazyColumn(val lazyListState: LazyListState) : MgoScaffoldScrollStateProvider()

    data class Preview(val canScrollForward: Boolean) : MgoScaffoldScrollStateProvider()
}

/**
 * Composable that shows a [Scaffold] with some custom logic build in.
 * Next to the functionality a  [Scaffold] has, this composable also supports:
 * - Showing a snackbar via [LocalSnackBarPresenter].
 * - Collapsable toolbar that has unlimited height (The default [MediumTopAppBar] only supports fixed heights).
 * - Support for two buttons that are fixed on the bottom. If the content overlaps these buttons, these buttons
 * will have a background color to let them stand from the content. If not, it will look like it's part of the content.
 * @param appBarTitle The title of the [MediumTopAppBar].
 * @param appBarTitleAlign The position of the title in the [MediumTopAppBar].
 * @param bottomBar The bottom bar to display.
 * @param scrollStateProvider Let's the Scaffold know if the content inside it is scrollable or not. This is used to determine
 * if the bottom buttons should have a background color or not. Default to [MgoScaffoldScrollStateProvider.None].
 * @param primaryButtonText If set, will show a primary button with this text. Default to null.
 * @param primaryButtonTheme The theme of the primary button. Defaults to [MgoButtonTheme.PRIMARY_DEFAULT].
 * @param primaryButtonLoading If set to true, will display a progress loader next to the primary button text. Default to false.
 * @param onPrimaryButtonClick Called when clicking the primary button. Defaults to null.
 * @param secondaryButtonText If set, will show a secondary button with this text. Default to null.
 * @param onSecondaryButtonClick Called when clicking the secondary button. Defaults to null.
 * @param onNavigateBack Called when clicking the back button. Default to null.
 * @param horizontalPadding The horizontal padding of the content. Default to 16 dp.
 */
@Composable
fun MgoScaffold(
    appBarTitle: String? = null,
    appBarTitleAlign: TextAlign = TextAlign.Start,
    bottomBar: @Composable () -> Unit = {},
    scrollStateProvider: MgoScaffoldScrollStateProvider = MgoScaffoldScrollStateProvider.None,
    primaryButtonText: String? = null,
    primaryButtonTheme: MgoButtonTheme = MgoButtonTheme.PRIMARY_DEFAULT,
    primaryButtonLoading: Boolean = false,
    onPrimaryButtonClick: (() -> Unit)? = null,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
    onNavigateBack: (() -> Unit)? = null,
    horizontalPadding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var measured by remember { mutableStateOf(false) }
    val scaffoldModifier =
        if (!measured) {
            Modifier
        } else {
            Modifier.nestedScroll(
                scrollBehavior.nestedScrollConnection,
            )
        }
    val snackBarHostState = remember { SnackbarHostState() }
    if (!LocalInspectionMode.current) {
        val snackbarPresenter = LocalSnackBarPresenter.current
        LaunchedEffect(Unit) {
            val visuals = snackbarPresenter.consume()
            if (visuals != null) {
                context.vibrate(MgoVibrateDuration.SHORT)
                snackBarHostState.showSnackbar(visuals = visuals)
            }
        }
    }
    val canScroll =
        when (scrollStateProvider) {
            is MgoScaffoldScrollStateProvider.Column ->
                scrollStateProvider.scrollState.canScrollForward ||
                    scrollStateProvider.scrollState.canScrollBackward

            is MgoScaffoldScrollStateProvider.LazyColumn ->
                scrollStateProvider.lazyListState.canScrollForward ||
                    scrollStateProvider.lazyListState.canScrollBackward

            MgoScaffoldScrollStateProvider.None -> false
            is MgoScaffoldScrollStateProvider.Preview -> false
        }

    Scaffold(
        modifier = scaffoldModifier,
        topBar = {
            appBarTitle?.let {
                val adjustedTypography =
                    MgoTypography.copy(
                        titleLarge = MaterialTheme.typography.titleLarge,
                        headlineSmall = MaterialTheme.typography.headlineLarge,
                    )
                MgoTheme(typography = adjustedTypography, isDarkTheme = LocalAppThemeProvider.current.appTheme.isDarkTheme()) {
                    MediumTopAppBar(
                        modifier =
                            Modifier.onGloballyPositioned {
                                measured = true
                            },
                        title = {
                            Text(
                                modifier =
                                    Modifier
                                        .fillMaxWidth(),
                                text = appBarTitle,
                                textAlign = appBarTitleAlign,
                            )
                        },
                        expandedHeight =
                            calculateExpandedHeight(
                                title = appBarTitle,
                            ),
                        // Add 16dp for some bottom padding
                        navigationIcon = {
                            onNavigateBack?.let {
                                IconButton(onClick = it) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                        contentDescription = stringResource(CopyR.string.common_previous),
                                        tint = MaterialTheme.colorScheme.symbolsPrimary(),
                                    )
                                }
                            }
                        },
                        colors =
                            TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                scrolledContainerColor = MaterialTheme.colorScheme.background,
                            ),
                        scrollBehavior = scrollBehavior,
                    )
                }
            }
        },
        bottomBar = bottomBar,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                MgoSnackBar(visuals = it.visuals as MgoSnackBarVisuals, onDismiss = { snackBarHostState.currentSnackbarData?.dismiss() })
            }
        },
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .consumeWindowInsets(innerPadding)
                        .padding(innerPadding)
                        .imePadding(),
            ) {
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = horizontalPadding)
                            .then(
                                if (scrollStateProvider is MgoScaffoldScrollStateProvider.Column && canScroll) {
                                    Modifier.verticalScroll(scrollStateProvider.scrollState)
                                } else {
                                    Modifier
                                },
                            ),
                ) {
                    content()
                }

                if (primaryButtonText != null && onPrimaryButtonClick != null) {
                    // Disable shadow for all previews that use this composable (excepting being this one)
                    val canScroll =
                        if (LocalInspectionMode.current && scrollStateProvider !is MgoScaffoldScrollStateProvider.Preview) {
                            false
                        } else {
                            canScroll
                        }
                    Buttons(
                        canScroll = canScroll,
                        primaryButtonText = primaryButtonText,
                        primaryButtonTheme = primaryButtonTheme,
                        primaryButtonLoading = primaryButtonLoading,
                        onPrimaryButtonClick = onPrimaryButtonClick,
                        secondaryButtonText = secondaryButtonText,
                        onSecondaryButtonClick = onSecondaryButtonClick,
                        horizontalPadding = horizontalPadding,
                    )
                }
            }
        },
    )
}

// A MediumTopAppBar expects an expandedHeight in dps. For this app we want it be a tall as the content (the title),
// but the MediumTopAppBar composable does not support something like that out of the box
@Composable
private fun calculateExpandedHeight(title: String): Dp {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val style = MaterialTheme.typography.headlineLarge
    val adjustedFontSize = style.fontSize * fontScale
    val constraintsWidth = with(density) { (configuration.screenWidthDp.dp - 20.dp).roundToPx() }
    val textMeasurer = rememberTextMeasurer()
    val expandedHeightPx =
        textMeasurer.measure(
            constraints = Constraints(maxWidth = constraintsWidth),
            text = title,
            style = style.copy(fontSize = adjustedFontSize),
        ).size.height
    return density.run { expandedHeightPx.toDp() } + TopAppBarDefaults.MediumAppBarCollapsedHeight + 16.dp
}

@Composable
private fun Buttons(
    horizontalPadding: Dp,
    canScroll: Boolean,
    primaryButtonText: String,
    primaryButtonTheme: MgoButtonTheme,
    primaryButtonLoading: Boolean,
    onPrimaryButtonClick: () -> Unit,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
) {
    if (canScroll) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .shadow(elevation = 1.dp, spotColor = Color.Gray),
        )
    }
    val background = if (canScroll) MaterialTheme.colorScheme.surface else Color.Transparent
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(background)
                .padding(horizontal = horizontalPadding, vertical = 16.dp),
    ) {
        if (secondaryButtonText != null && onSecondaryButtonClick != null) {
            MgoButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                buttonText = secondaryButtonText,
                onClick = onSecondaryButtonClick,
                buttonTheme = MgoButtonTheme.SECONDARY_DEFAULT,
            )
        }
        MgoButton(
            modifier =
                Modifier
                    .fillMaxWidth(),
            isLoading = primaryButtonLoading,
            buttonText = primaryButtonText,
            onClick = onPrimaryButtonClick,
            buttonTheme = primaryButtonTheme,
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoScaffoldWithAppBarAndBackButton() {
    MgoTheme {
        MgoScaffold(
            appBarTitle = "App Bar Title",
            onNavigateBack = {},
            horizontalPadding = 16.dp,
            content = {
                Text("Hello World")
            },
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoScaffoldWithAppBar() {
    MgoTheme {
        MgoScaffold(
            appBarTitle = "App Bar Title",
            horizontalPadding = 16.dp,
            content = {
                Text("Hello World")
            },
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoScaffoldWithoutAppBar() {
    MgoTheme {
        Box(modifier = Modifier.padding(top = 16.dp)) {
            MgoScaffold(
                horizontalPadding = 16.dp,
                content = {
                    Text("Hello World")
                },
            )
        }
    }
}

@PreviewLightDark
@Composable
internal fun MgoScaffoldWithPrimaryButton() {
    MgoTheme {
        MgoScaffold(
            appBarTitle = "App Bar Title",
            horizontalPadding = 16.dp,
            content = {
                Text("Hello World")
            },
            primaryButtonText = "Primary Button",
            onPrimaryButtonClick = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoScaffoldWithPrimaryButtonScrollable() {
    MgoTheme {
        MgoScaffold(
            appBarTitle = "App Bar Title",
            horizontalPadding = 16.dp,
            content = {
                Text("Hello World")
            },
            primaryButtonText = "Primary Button",
            onPrimaryButtonClick = {},
            scrollStateProvider = MgoScaffoldScrollStateProvider.Preview(true),
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoScaffoldWithPrimaryAndSecondaryButton() {
    MgoTheme {
        MgoScaffold(
            appBarTitle = "App Bar Title",
            horizontalPadding = 16.dp,
            content = {
                Text("Hello World")
            },
            primaryButtonText = "Primary Button",
            onPrimaryButtonClick = {},
            secondaryButtonText = "Secondary Button",
            onSecondaryButtonClick = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoScaffoldWithPrimaryAndSecondaryButtonScrollable() {
    MgoTheme {
        MgoScaffold(
            appBarTitle = "App Bar Title",
            horizontalPadding = 16.dp,
            content = {
                Text("Hello World")
            },
            primaryButtonText = "Primary Button",
            onPrimaryButtonClick = {},
            secondaryButtonText = "Secondary Button",
            onSecondaryButtonClick = {},
            scrollStateProvider = MgoScaffoldScrollStateProvider.Preview(true),
        )
    }
}
