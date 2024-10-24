package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.fonts
import nl.rijksoverheid.mgo.component.theme.iconsPrimary

@Composable
fun DashboardBottomBarScreen(
    overviewTab: @Composable () -> Unit,
    organizationsTab: @Composable () -> Unit,
    aboutThisAppTab: @Composable () -> Unit,
) {
    var selectedBottomBarItem by rememberSaveable { mutableStateOf(BottomBarItem.OVERVIEW) }
    MgoScaffold(
        appBarTitle = stringResource(selectedBottomBarItem.titleId),
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedBottomBarItem,
                onSelectBottomBarItem = { selectedItem ->
                    selectedBottomBarItem = selectedItem
                },
            )
        },
        content = {
            when (selectedBottomBarItem) {
                BottomBarItem.OVERVIEW -> {
                    overviewTab()
                }

                BottomBarItem.ORGANIZATIONS -> {
                    organizationsTab()
                }

                BottomBarItem.ABOUT_THIS_APP -> {
                    aboutThisAppTab()
                }
            }
        },
    )
}

@Composable
private fun BottomNavigationBar(
    selectedItem: BottomBarItem,
    onSelectBottomBarItem: (item: BottomBarItem) -> Unit,
) {
    val bottomBarItemTextStyle =
        TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
        )
    // See [MgoCard] why this is done.
    val elevation = if (isSystemInDarkTheme()) 0.dp else 1.dp
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.backgroundSecondary(),
        contentColor = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
    ) {
        BottomBarItem.entries.forEach { item ->
            NavigationBarItem(
                icon = {
                    val iconId =
                        if (item == selectedItem) {
                            item.selectedIconId
                        } else {
                            item.deselectedIconId
                        }
                    Icon(painter = painterResource(id = iconId), contentDescription = null)
                },
                label = { Text(stringResource(item.titleId), style = bottomBarItemTextStyle) },
                selected = item == selectedItem,
                onClick = { onSelectBottomBarItem(item) },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
                        selectedTextColor = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
                        unselectedIconColor = MaterialTheme.colorScheme.iconsPrimary(),
                        unselectedTextColor = MaterialTheme.colorScheme.iconsPrimary(),
                    ),
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun DashboardBottomBarScreenPreview() {
    MgoTheme {
        BottomNavigationBar(selectedItem = BottomBarItem.OVERVIEW, onSelectBottomBarItem = {})
    }
}
