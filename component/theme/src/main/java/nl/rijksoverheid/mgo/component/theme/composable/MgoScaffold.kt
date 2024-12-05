package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.MgoTypography
import nl.rijksoverheid.mgo.component.theme.MgoVibrateDuration
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.snackbar.LocalSnackbarPresenter
import nl.rijksoverheid.mgo.component.theme.snackbar.MgoSnackBar
import nl.rijksoverheid.mgo.component.theme.snackbar.MgoSnackBarVisuals
import nl.rijksoverheid.mgo.component.theme.vibrate
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

sealed class MgoScaffoldScrollStateProvider(open val canScrollForward: Boolean) {
    data object None : MgoScaffoldScrollStateProvider(false)

    data class Column(val scrollState: ScrollState) : MgoScaffoldScrollStateProvider(scrollState.canScrollForward)

    data class LazyColumn(val lazyListState: LazyListState) : MgoScaffoldScrollStateProvider(lazyListState.canScrollForward)

    data class Preview(override val canScrollForward: Boolean) : MgoScaffoldScrollStateProvider(canScrollForward)
}

@Composable
fun MgoScaffold(
    appBarTitle: String? = null,
    appBarTitleAlign: TextAlign = TextAlign.Start,
    bottomBar: @Composable () -> Unit = {},
    scrollStateProvider: MgoScaffoldScrollStateProvider = MgoScaffoldScrollStateProvider.None,
    primaryButtonText: String? = null,
    onPrimaryButtonClick: (() -> Unit)? = null,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
    onNavigateBack: (() -> Unit)? = null,
    isRootScaffold: Boolean = true,
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
        val snackbarPresenter = LocalSnackbarPresenter.current
        LaunchedEffect(Unit) {
            val visuals = snackbarPresenter.consume()
            if (visuals != null) {
                context.vibrate(MgoVibrateDuration.SHORT)
                snackBarHostState.showSnackbar(visuals = visuals)
            }
        }
    }

    Scaffold(
        modifier = scaffoldModifier,
        topBar = {
            appBarTitle?.let {
                val adjustedTypography =
                    MgoTypography.copy(
                        titleLarge =
                            MaterialTheme.typography.bodySmall.copy(
                                fontWeight =
                                    FontWeight
                                        .Bold,
                            ),
                        headlineSmall = MaterialTheme.typography.headingLarge,
                    )
                MgoTheme(typography = adjustedTypography) {
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
                                horizontalPadding = horizontalPadding,
                            ),
                        // Add 16dp for some bottom padding
                        navigationIcon = {
                            onNavigateBack?.let {
                                IconButton(onClick = it) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                        contentDescription = stringResource(CopyR.string.common_previous),
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
                MgoSnackBar(visuals = it.visuals as MgoSnackBarVisuals, dismiss = { snackBarHostState.currentSnackbarData?.dismiss() })
            }
        },
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .then(if (isRootScaffold) Modifier.consumeWindowInsets(innerPadding) else Modifier)
                        .padding(innerPadding),
            ) {
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = horizontalPadding)
                            .then(
                                if (scrollStateProvider is MgoScaffoldScrollStateProvider.Column) {
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
                    val canScrollForward =
                        if (LocalInspectionMode.current && scrollStateProvider !is MgoScaffoldScrollStateProvider.Preview) {
                            false
                        } else {
                            scrollStateProvider.canScrollForward
                        }
                    Buttons(
                        canScrollForward = canScrollForward,
                        primaryButtonText = primaryButtonText,
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

/**
 * A MediumTopAppBar expects an expandedHeight in dps. For this app we want it be a tall as the content (the title),
 * but the MediumTopAppBar composable does not support something like that out of the box
 */
@Composable
private fun calculateExpandedHeight(
    title: String,
    horizontalPadding: Dp,
): Dp {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val constraintsWidth = with(density) { (configuration.screenWidthDp.dp - horizontalPadding).toPx().toInt() }
    val textMeasurer = rememberTextMeasurer()
    val expandedHeightPx =
        textMeasurer.measure(
            constraints = Constraints(maxWidth = constraintsWidth),
            text = title,
            style = MaterialTheme.typography.headingLarge,
        ).size.height
    return density.run { expandedHeightPx.toDp() } + TopAppBarDefaults.MediumAppBarCollapsedHeight + 16.dp
}

@Composable
private fun Buttons(
    horizontalPadding: Dp,
    canScrollForward: Boolean,
    primaryButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
) {
    if (canScrollForward) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .shadow(elevation = 1.dp, spotColor = Color.Gray),
        )
    }
    val background = if (canScrollForward) MaterialTheme.colorScheme.surface else Color.Transparent
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
            buttonText = primaryButtonText,
            onClick = onPrimaryButtonClick,
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
