package nl.rijksoverheid.mgo.feature.localisation.addOrganization

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.composable.MgoBasicTextField
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

const val TEST_TAG_NAME_TEXT_FIELD = "NAME_TEXT_FIELD"
const val TEST_TAG_CITY_TEXT_FIELD = "CITY_TEXT_FIELD"

@Composable
fun AddOrganizationScreen(
    onNavigateBack: () -> Unit,
    onNavigateToOrganizationSearch: (name: String, city: String) -> Unit,
) {
    val viewModel: AddOrganizationScreenViewModel = hiltViewModel()
    val viewState: AddOrganizationScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigation.collectLatest {
            onNavigateToOrganizationSearch(viewState.name, viewState.city)
        }
    }

    AddOrganizationScreenContent(
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
private fun AddOrganizationScreenContent(
    viewState: AddOrganizationScreenViewState,
    onNavigateBack: () -> Unit,
    onSetName: (name: String) -> Unit,
    onSetCity: (city: String) -> Unit,
    onSearch: () -> Unit,
) {
    MgoScaffold(
        appBarTitle = stringResource(id = CopyR.string.add_organization_heading),
        onNavigateBack = onNavigateBack,
        content = {
            ColumnWithButtons(
                buttonText = stringResource(id = CopyR.string.common_search),
                onButtonClick = onSearch,
            ) {
                MgoBasicTextField(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    value = viewState.name,
                    header =
                        stringResource(
                            id = CopyR.string.add_organization_name,
                        ),
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
                    header =
                        stringResource(
                            id = CopyR.string.add_organization_city,
                        ),
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
internal fun AddOrganizationScreenPreview() {
    MgoTheme {
        AddOrganizationScreenContent(
            viewState =
                AddOrganizationScreenViewState(
                    name = "Tandarts Tandje Erbij",
                    city = "Roermond",
                    nameError = null,
                    cityError = null,
                ),
            onNavigateBack = {},
            onSetName = {},
            onSetCity = {},
            onSearch = {},
        )
    }
}
