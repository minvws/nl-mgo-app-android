package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.LayoutDirection
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

@Composable
fun MgoScaffold(
    appBarTitle: String? = null,
    appBarTitleAlign: TextAlign = TextAlign.Start,
    bottomBar: @Composable () -> Unit = {},
    onNavigateBack: (() -> Unit)? = null,
    scrollable: Boolean = false,
    isRootScaffold: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
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
                                horizontalPadding =
                                    contentPadding.calculateStartPadding(LayoutDirection.Ltr)
                                        .plus(contentPadding.calculateEndPadding(LayoutDirection.Ltr)),
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
                        .padding(innerPadding)
                        .padding(contentPadding)
                        .then(
                            if (scrollable) {
                                Modifier.verticalScroll(rememberScrollState())
                            } else {
                                Modifier
                            },
                        ),
            ) {
                content()
            }
        },
    )
}

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

@PreviewLightDark
@Composable
internal fun MgoScaffoldWithAppBarAndBackButton() {
    MgoTheme {
        MgoScaffold(
            appBarTitle = "App Bar Title",
            onNavigateBack = {},
            contentPadding = PaddingValues(16.dp),
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
            contentPadding = PaddingValues(16.dp),
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
        MgoScaffold(
            contentPadding = PaddingValues(16.dp),
            content = {
                Text("Hello World")
            },
        )
    }
}
