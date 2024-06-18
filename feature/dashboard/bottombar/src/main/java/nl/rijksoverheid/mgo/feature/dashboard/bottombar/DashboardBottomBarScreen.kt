package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefault
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.fonts
import nl.rijksoverheid.mgo.component.theme.iconsPrimary

@Composable
fun DashboardBottomBarScreen(
    overviewTab: @Composable () -> Unit,
    aboutThisAppTab: @Composable () -> Unit,
) {
    var selectedBottomBarItem by remember { mutableStateOf<BottomBarItem>(BottomBarItem.Overview) }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedBottomBarItem,
                onSelectBottomBarItem = { selectedItem ->
                    selectedBottomBarItem = selectedItem
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier =
            Modifier
                .padding(paddingValues),
        ) {
            when (selectedBottomBarItem) {
                BottomBarItem.Overview -> {
                    overviewTab()
                }

                BottomBarItem.AboutThisApp -> {
                    aboutThisAppTab()
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
    // See [MgoCard] why this is done.
    val elevation = if (isSystemInDarkTheme()) 0.dp else 1.dp
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.backgroundSecondary(),
        contentColor = MaterialTheme.colors.actionTertiaryDefault(),
        elevation = elevation,
    ) {
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

@DefaultPreviews
@Composable
internal fun DashboardBottomBarScreenPreview() {
    MgoTheme {
        BottomNavigationBar(selectedItem = BottomBarItem.Overview, onSelectBottomBarItem = {})
    }
}
