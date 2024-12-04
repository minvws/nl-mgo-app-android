package nl.rijksoverheid.mgo.feature.localisation.addOrganization

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.composable.MgoBasicTextField
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffoldScrollStateProvider
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
    val (nameFocusRequester, cityFocusRequester) = FocusRequester.createRefs()
    LaunchedEffect(Unit) {
        nameFocusRequester.requestFocus()
    }
    MgoScaffold(
        appBarTitle = stringResource(id = CopyR.string.add_organization_heading),
        onNavigateBack = onNavigateBack,
        scrollStateProvider = MgoScaffoldScrollStateProvider.Column(rememberScrollState()),
        primaryButtonText = stringResource(id = CopyR.string.common_search),
        onPrimaryButtonClick = onSearch,
        content = {
            MgoBasicTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .focusRequester(nameFocusRequester),
                value = viewState.name,
                header =
                    stringResource(
                        id = CopyR.string.add_organization_name,
                    ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, capitalization = KeyboardCapitalization.Words),
                keyboardActions = KeyboardActions(onNext = { cityFocusRequester.requestFocus() }),
                onValueChange = onSetName,
                error = viewState.nameError?.let { resource -> stringResource(id = resource) },
                textFieldTestTag = TEST_TAG_NAME_TEXT_FIELD,
            )

            MgoBasicTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .focusRequester(cityFocusRequester)
                        .padding(top = 16.dp),
                value = viewState.city,
                header =
                    stringResource(
                        id = CopyR.string.add_organization_city,
                    ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search, capitalization = KeyboardCapitalization.Words),
                keyboardActions = KeyboardActions(onSearch = { onSearch() }),
                onValueChange = onSetCity,
                error = viewState.cityError?.let { resource -> stringResource(id = resource) },
                textFieldTestTag = TEST_TAG_CITY_TEXT_FIELD,
            )
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
