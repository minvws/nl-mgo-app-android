package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
    contentPadding: PaddingValues =
        PaddingValues(
            0.dp,
            0.dp,
            0.dp,
            0.dp,
        ),
    content: @Composable ColumnScope.() -> Unit,
) {
    val localDensity = LocalDensity.current
    var expandedAppBarHeight by remember { mutableStateOf(Int.MAX_VALUE.dp) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scaffoldModifier =
        if (expandedAppBarHeight == Int.MAX_VALUE.dp) {
            Modifier
        } else {
            Modifier.nestedScroll(
                scrollBehavior.nestedScrollConnection,
            )
        }
    Scaffold(
        modifier = scaffoldModifier,
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
                    expandedHeight = expandedAppBarHeight, // Add 16dp for some bottom padding
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
            Column(
                modifier =
                    Modifier
                        .consumeWindowInsets(paddingValues)
                        .padding(paddingValues)
                        .padding(top = if (appBarTitle == null) TopAppBarDefaults.MediumAppBarCollapsedHeight else 0.dp)
                        .padding(contentPadding),
            ) {
                content()
            }
        },
    )
}
