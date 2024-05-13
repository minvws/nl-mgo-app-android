package nl.rijksoverheid.mgo.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager

/**
 * Temporary place holder screen to show after the onboarding so you can reset the app to first launch state.
 */
@Composable
fun DashboardScreen() {
    val context = LocalContext.current
    val navigationManager = LocalNavigationManager.current
    val viewModel: DashboardViewModel = hiltViewModel()

    val dashboardNavController = rememberNavController()
    ProvideDashboardNavigationManager(navigationManager = DashboardNavigationManager(dashboardNavController)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Dashboard") },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues).padding(all = 16.dp).background(Color.Green),
            ) {
//                Button(onClick = { navigationManager.navigate(NavigationScreen.Localisation.Start) }) {
//                    Text(text = "Search")
//                }
//                Button(onClick = { viewModel.reset(context) }) {
//                    Text(text = "Reset")
//                }
            }
        }
    }
}
