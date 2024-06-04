package nl.rijksoverheid.mgo.feature.localisation.search

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
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

const val TEST_TAG_NAME_TEXT_FIELD = "NAME_TEXT_FIELD"
const val TEST_TAG_CITY_TEXT_FIELD = "CITY_TEXT_FIELD"

@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSearchResults: (name: String, city: String) -> Unit,
) {
    val viewModel: SearchScreenViewModel = hiltViewModel()
    val viewState: SearchScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigation.collectLatest { screen ->
            onNavigateToSearchResults(viewState.name, viewState.city)
        }
    }

    SearchScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
        onSetName = { name ->
            viewModel.setName(name)
        },
        onSetCity = { city ->
            viewModel.setCity(city)
        },
        onSearch = {
            viewModel.validate()
        },
    )
}

@Composable
private fun SearchScreenContent(
    viewState: SearchScreenViewState,
    onNavigateBack: () -> Unit,
    onSetName: (name: String) -> Unit,
    onSetCity: (city: String) -> Unit,
    onSearch: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = CopyR.string.general_previous),
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            ColumnWithButtons(
                modifier = Modifier.padding(innerPadding),
                buttonText = stringResource(id = CopyR.string.general_search),
                onButtonClick = onSearch,
            ) {
                Text(
                    text = stringResource(id = CopyR.string.localisation_search_title),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )

                MgoBasicTextField(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    value = viewState.name,
                    header = stringResource(id = CopyR.string.localisation_search_name_header),
                    onValueChange = onSetName,
                    error = viewState.nameError?.let { resource -> stringResource(id = resource) },
                    textFieldTestTag = TEST_TAG_NAME_TEXT_FIELD,
                )

                MgoBasicTextField(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    value = viewState.city,
                    header = stringResource(id = CopyR.string.localisation_search_city_header),
                    onValueChange = onSetCity,
                    error = viewState.cityError?.let { resource -> stringResource(id = resource) },
                    textFieldTestTag = TEST_TAG_CITY_TEXT_FIELD,
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
            onNavigateBack = {},
            onSetName = {},
            onSetCity = {},
            onSearch = {},
        )
    }
}
