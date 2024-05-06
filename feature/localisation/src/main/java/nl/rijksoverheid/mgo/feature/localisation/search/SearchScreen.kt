package nl.rijksoverheid.mgo.feature.localisation.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.composable.MgoBasicTextField
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationManager
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen() {
    val navigationManager = LocalNavigationManager.current
    val searchScreenViewModel: SearchScreenViewModel = hiltViewModel()
    val viewState: SearchScreenViewState by searchScreenViewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        // Handle navigation
        searchScreenViewModel.navigation.collectLatest { screen ->
            navigationManager.navigate(screen)
        }
    }

    SearchScreenContent(
        viewState = viewState,
        onSetName = { name ->
            searchScreenViewModel.setName(name)
        },
        onSetCity = { city ->
            searchScreenViewModel.setCity(city)
        },
        onSearch = {
            searchScreenViewModel.getSearchResults()
        },
    )
}

@Composable
private fun SearchScreenContent(
    viewState: SearchScreenViewState,
    onSetName: (name: String) -> Unit,
    onSetCity: (city: String) -> Unit,
    onSearch: () -> Unit,
) {
    val navigationManager: NavigationManager = LocalNavigationManager.current
    Scaffold(
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
            ColumnWithButtons(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp),
                buttonText = stringResource(id = R.string.general_search),
                onButtonClick = onSearch,
            ) {
                Text(
                    text = stringResource(id = R.string.localisation_search_title),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )

                MgoBasicTextField(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    value = viewState.name,
                    header = stringResource(id = R.string.localisation_search_name_header),
                    onValueChange = onSetName,
                    error = viewState.nameError?.let { resource -> stringResource(id = resource) },
                )

                MgoBasicTextField(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    value = viewState.city,
                    header = stringResource(id = R.string.localisation_search_city_header),
                    onValueChange = onSetCity,
                    error = viewState.cityError?.let { resource -> stringResource(id = resource) },
                )
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun SearchScreenPreview() {
    MgoTheme {
        SearchScreenContent(
            viewState = SearchScreenViewState(name = "Tandarts Tandje Erbij", city = "Roermond", nameError = null, cityError = null),
            onSetName = {},
            onSetCity = {},
            onSearch = {},
        )
    }
}
