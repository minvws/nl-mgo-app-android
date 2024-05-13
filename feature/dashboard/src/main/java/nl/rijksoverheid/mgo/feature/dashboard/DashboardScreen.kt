package nl.rijksoverheid.mgo.feature.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import nl.rijksoverheid.mgo.component.theme.fonts
import nl.rijksoverheid.mgo.component.theme.iconsPrimary
import nl.rijksoverheid.mgo.feature.dashboard.overview.OverviewTabScreen

@Composable
fun DashboardScreen(onNavigateToLocalisation: () -> Unit) {
    val navController = rememberNavController()
    var selectedBottomBarItem by remember { mutableStateOf<BottomBarItem>(BottomBarItem.Overview) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Dashboard") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedBottomBarItem,
                onSelectBottomBarItem = { selectedItem ->
                    selectedBottomBarItem = selectedItem
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .padding(paddingValues),
        ) {
            when (selectedBottomBarItem) {
                BottomBarItem.Overview -> {
                    OverviewTabScreen(navController = navController, onNavigateToLocalisation = onNavigateToLocalisation)
                }

                BottomBarItem.AboutThisApp -> {
                    AboutThisAppScreen()
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    selectedItem: BottomBarItem,
    onSelectBottomBarItem: (item: BottomBarItem) -> Unit,
) {
    val items = listOf(BottomBarItem.Overview, BottomBarItem.AboutThisApp)
    val bottomBarItemTextStyle =
        TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
        )
    BottomNavigation(backgroundColor = Color.White, contentColor = MaterialTheme.colors.primary) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = item.iconId), contentDescription = null) },
                label = { Text(stringResource(item.titleId), style = bottomBarItemTextStyle) },
                selected = item == selectedItem,
                onClick = {
                    onSelectBottomBarItem(item)
                },
                unselectedContentColor = MaterialTheme.colors.iconsPrimary(),
            )
        }
    }
}
