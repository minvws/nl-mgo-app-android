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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen

/**
 * Temporary place holder screen to show after the onboarding so you can reset the app to first launch state.
 */
@Composable
fun DashboardScreen() {
    val context = LocalContext.current
    val navigationManager = LocalNavigationManager.current
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
        Column(
            modifier = Modifier.padding(paddingValues).padding(all = 16.dp),
        ) {
            Button(onClick = { navigationManager.navigate(NavigationScreen.Localisation.Start) }) {
                Text(text = "Search")
            }
            Button(onClick = { viewModel.reset(context) }) {
                Text(text = "Reset")
            }
        }
    }
}
