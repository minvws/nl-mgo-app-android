package nl.rijksoverheid.mgo.feature.localisation.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButton
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager

@Composable
fun SearchScreen() {
    val viewModel: SearchScreenViewModel = hiltViewModel()
    val viewState: SearchScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()
    SearchScreenContent(
        viewState = viewState,
        onSetName = { name ->
            viewModel.setName(name)
        },
        onSetCity = { city ->
            viewModel.setCity(city)
        },
    )
}

@Composable
private fun SearchScreenContent(
    viewState: SearchScreenViewState,
    onSetName: (name: String) -> Unit,
    onSetCity: (city: String) -> Unit,
) {
    val navigationManager: NavigationManager = LocalNavigationManager.current
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { navigationManager.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.general_previous),
                        )
                    }
                },
            )
        },
        backgroundColor = Color.Transparent,
        content = { innerPadding ->
            ColumnWithButton(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp),
                buttonText = stringResource(id = R.string.general_search),
                onButtonClick = { },
            ) {
                TextField(
                    value = viewState.name,
                    onValueChange = onSetName,
                    label = {
                        Text(
                            stringResource(id = R.string.localisation_search_name_hint),
                        )
                    },
                )
                TextField(
                    value = viewState.city,
                    onValueChange = onSetCity,
                    label = {
                        Text(
                            stringResource(id = R.string.localisation_search_city_hint),
                        )
                    },
                )
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun NameAndCity() {
    MgoTheme {
        SearchScreenContent(
            viewState = SearchScreenViewState(name = "Tandarts", city = "Rotterdam", nameError = null, cityError = null),
            onSetName = {},
            onSetCity = {},
        )
    }
}
