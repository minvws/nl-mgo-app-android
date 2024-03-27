package nl.rijksoverheid.mgo.feature.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Temporary place holder screen to show after the onboarding so you can reset the app to first launch state.
 */
@Composable
fun DashboardScreen() {
    val viewModel: DashboardViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Dashboard") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
            )
        },
        backgroundColor = Color.Transparent,
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(all = 16.dp)) {
            Button(onClick = { viewModel.reset() }) {
                Text(text = "Reset")
            }
        }
    }
}
