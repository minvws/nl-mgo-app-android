package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun MgoScaffold(
    appBarTitle: String? = null,
    onNavigateBack: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val localDensity = LocalDensity.current
    var expandedAppBarHeight by remember { mutableStateOf(Int.MAX_VALUE.dp) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            appBarTitle?.let {
                MediumTopAppBar(
                    title = {
                        Text(
                            modifier =
                                Modifier.onGloballyPositioned {
                                    val heightDp = with(localDensity) { it.size.height.toDp() }
                                    if (heightDp != 0.dp) {
                                        expandedAppBarHeight = heightDp + TopAppBarDefaults.MediumAppBarCollapsedHeight
                                    }
                                },
                            text = appBarTitle,
                        )
                    },
                    expandedHeight = expandedAppBarHeight + 16.dp, // Add 16dp for some bottom padding
                    navigationIcon = {
                        onNavigateBack?.let {
                            IconButton(onClick = it) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = "Vorige",
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
        },
        content = { paddingValues ->
            // If no AppBar is present, we still want to content to appear under a AppBar (so that the content is aligned with screens
            // that do have a AppBar). In order to do that, we top padding to the content that matches the height of a TopAppBar.
            val bottomPadding = if (appBarTitle == null) TopAppBarDefaults.TopAppBarExpandedHeight else 0.dp
            Box(modifier = Modifier.consumeWindowInsets(paddingValues).padding(paddingValues).padding(PaddingValues(top = bottomPadding))) {
                content()
            }
        },
    )
}
