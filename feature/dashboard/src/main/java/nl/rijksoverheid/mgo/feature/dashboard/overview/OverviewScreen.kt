package nl.rijksoverheid.mgo.feature.dashboard.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import nl.rijksoverheid.mgo.feature.dashboard.navigation.DashboardNavigationScreen
import nl.rijksoverheid.mgo.feature.dashboard.navigation.LocalDashboardNavigationManager

@Composable
fun OverviewScreen(onNavigateToLocalisation: () -> Unit) {
    val navigationManager = LocalDashboardNavigationManager.current
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Red)
                .clickable {
                    navigationManager.navigate(
                        DashboardNavigationScreen.Detail,
                    )
                },
    ) {
        Button(onClick = { onNavigateToLocalisation() }) {
            Text(text = "Localisation")
        }
    }
}
