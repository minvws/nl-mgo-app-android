package nl.rijksoverheid.mgo.feature.localisation.addOrganization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoBasicTextField
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

const val TEST_TAG_NAME_TEXT_FIELD = "NAME_TEXT_FIELD"
const val TEST_TAG_CITY_TEXT_FIELD = "CITY_TEXT_FIELD"

/**
 * Composable that shows a screen where you can add a health care provider.
 *
 * @param onNavigateBack Called when requested to navigate back.
 * @param onNavigateToOrganizationSearch Called when requested to navigate to the screen where to show the results.
 */
@Composable
fun AddOrganizationScreen(
  onNavigateBack: (() -> Unit)?,
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
  onNavigateBack: (() -> Unit)?,
  onSetName: (name: String) -> Unit,
  onSetCity: (city: String) -> Unit,
  onSearch: () -> Unit,
) {
  val scrollState = rememberScrollState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(scrollState.canScrollForward, scrollState.canScrollBackward)

  val (nameFocusRequester, cityFocusRequester) = FocusRequester.createRefs()
  LaunchedEffect(Unit) {
    nameFocusRequester.requestFocus()
  }

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).imePadding(),
    contentWindowInsets = WindowInsets.statusBars,
    topBar = {
      MgoLargeTopAppBar(
        title = stringResource(id = CopyR.string.add_organization_heading),
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
      )
    },
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        Column(
          modifier =
            Modifier
              .weight(1f)
              .verticalScroll(scrollState)
              .padding(16.dp),
        ) {
          MgoBasicTextField(
            modifier =
              Modifier
                .fillMaxWidth()
                .focusRequester(nameFocusRequester),
            value = viewState.name,
            heading = "${stringResource(id = CopyR.string.add_organization_name)} ${stringResource(id = CopyR.string.common_required)}",
            keyboardOptions =
              KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words,
              ),
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
            heading =
              "${stringResource(id = CopyR.string.add_organization_city)} ${stringResource(id = CopyR.string.common_required)}",
            keyboardOptions =
              KeyboardOptions(
                imeAction = ImeAction.Search,
                capitalization = KeyboardCapitalization.Words,
              ),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            onValueChange = onSetCity,
            error = viewState.cityError?.let { resource -> stringResource(id = resource) },
            textFieldTestTag = TEST_TAG_CITY_TEXT_FIELD,
          )
        }

        MgoBottomButtons(
          primaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.common_search),
              onClick = onSearch,
            ),
          isElevated = scrollState.canScrollForward,
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
