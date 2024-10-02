package nl.rijksoverheid.mgo.component.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MgoSnackBarScaffold(
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    if (LocalInspectionMode.current) {
        Scaffold(
            topBar = topBar,
            content = content,
        )
    } else {
        val viewModel = hiltViewModel<MgoSnackBarScaffoldViewModel>()
        val snackBarHostState = remember { SnackbarHostState() }
        LaunchedEffect(Unit) {
            viewModel.visuals.collectLatest { visuals ->
                snackBarHostState.showSnackbar(visuals = visuals)
            }
        }

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState) {
                    MgoSnackBar(visuals = it.visuals as MgoSnackBarVisuals)
                }
            },
            topBar = topBar,
            content = content,
        )
    }
}
