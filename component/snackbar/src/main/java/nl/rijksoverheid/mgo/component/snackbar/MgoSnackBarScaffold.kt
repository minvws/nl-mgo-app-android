package nl.rijksoverheid.mgo.component.snackbar

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MgoSnackbarScaffold(
    appBarTitle: String? = null,
    appBarTitleAlign: TextAlign = TextAlign.Start,
    bottomBar: @Composable () -> Unit = {},
    onNavigateBack: (() -> Unit)? = null,
    contentPadding: PaddingValues =
        PaddingValues(
            16.dp,
            0.dp,
            16.dp,
            0.dp,
        ),
    scrollable: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    if (!LocalInspectionMode.current) {
        val viewModel = hiltViewModel<MgoSnackBarScaffoldViewModel>()
        LaunchedEffect(Unit) {
            viewModel.visuals.collectLatest { visuals ->
                snackBarHostState.showSnackbar(visuals = visuals)
            }
        }
    }
    MgoScaffold(
        appBarTitle = appBarTitle,
        appBarTitleAlign = appBarTitleAlign,
        bottomBar = bottomBar,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                MgoSnackBar(visuals = it.visuals as MgoSnackBarVisuals, dismiss = { snackBarHostState.currentSnackbarData?.dismiss() })
            }
        },
        onNavigateBack = onNavigateBack,
        contentPadding = contentPadding,
        scrollable = scrollable,
        content = content,
    )
}
