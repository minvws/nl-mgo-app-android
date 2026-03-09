package nl.rijksoverheid.mgo.feature.localisation.manual

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.organization.Organization
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.component.theme.BackgroundsSecondary
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.SymbolsSecondary
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun ManualLocalisationScreen(
  onNavigateToDashboard: () -> Unit,
  onNavigateBack: (() -> Unit)?,
) {
  val viewModel: ManualLocalisationScreenViewModel = hiltViewModel()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    viewModel.navigateToDashboard.collectLatest {
      onNavigateToDashboard()
    }
  }

  var showAddOrganizationDialog by remember { mutableStateOf<Organization?>(null) }
  showAddOrganizationDialog?.let { organization ->
    MgoAlertDialog(
      heading = stringResource(CopyR.string.search_organization_dialog_heading, organization.displayName),
      subHeading = stringResource(CopyR.string.search_organization_dialog_subheading),
      positiveButtonText = stringResource(CopyR.string.search_organization_dialog_yes),
      positiveButtonTextColor = MaterialTheme.colorScheme.ActionsGhostText(),
      negativeButtonText = stringResource(CopyR.string.search_organization_dialog_no),
      onClickPositiveButton = {
        viewModel.add(organization)
        showAddOrganizationDialog = null
      },
      onClickNegativeButton = { showAddOrganizationDialog = null },
      onDismissRequest = { showAddOrganizationDialog = null },
    )
  }

  ManualLocalisationScreenContent(
    viewState = viewState,
    onSearch = { query ->
      viewModel.search(query)
    },
    onAddOrganization = { organization ->
      showAddOrganizationDialog = organization
    },
    onNavigateBack = onNavigateBack,
  )
}

@Composable
private fun ManualLocalisationScreenContent(
  viewState: ManualLocalisationScreenViewState,
  onSearch: (query: String) -> Unit,
  onAddOrganization: (organization: Organization) -> Unit,
  onNavigateBack: (() -> Unit)?,
) {
  val lazyListState = rememberLazyListState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(lazyListState.canScrollForward, lazyListState.canScrollBackward)
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      val title = if (onNavigateBack == null) CopyR.string.search_organization_onboarding_heading else CopyR.string.search_organization_heading
      MgoLargeTopAppBar(
        title = stringResource(title),
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
      )
    },
    content = { contentPadding ->
      LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).padding(contentPadding), state = lazyListState) {
        item {
          Text(
            text = stringResource(id = CopyR.string.search_organization_subheading),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.LabelsSecondary(),
          )
        }

        item {
          val textFieldState = rememberTextFieldState()
          SearchTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 32.dp),
            loading = viewState.loading,
            textFieldState = textFieldState,
            onSearch = onSearch,
          )
        }

        if (viewState.organizations.isEmpty()) {
          item(key = "empty") {
            ManualLocalisationScreenEmpty(modifier = Modifier.padding(top = 32.dp).animateItem())
          }
        } else {
          item {
            Text(
              modifier = Modifier.padding(start = 16.dp),
              text = stringResource(CopyR.string.search_organization_result_count, viewState.organizations.size),
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.LabelsSecondary(),
            )
          }

          items(viewState.organizations.size, key = { viewState.organizations[it].organization.id }) { position ->
            val organizationUi = viewState.organizations[position]
            val organization = organizationUi.organization
            val trailing =
              when {
                organization.added == true -> stringResource(CopyR.string.search_organization_already_added)
                !organizationUi.supported -> stringResource(CopyR.string.search_organization_not_participating)
                else -> null
              }
            ManualLocalisationCard(
              modifier = Modifier.padding(top = 8.dp).animateItem(),
              heading = organization.displayName,
              subheading =
                buildString {
                  val address = organization.addressLine
                  if (address != null) {
                    append(address)
                    append(", ")
                  }
                  append(organization.city)
                },
              trailing = trailing,
              onClick =
                if (trailing == null) {
                  { onAddOrganization(organization) }
                } else {
                  null
                },
            )
          }
        }
      }
    },
  )
}

@Composable
private fun ManualLocalisationScreenEmpty(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
      Image(painter = painterResource(R.drawable.illustration_empty), contentDescription = null)
      Text(
        modifier = Modifier.padding(top = 24.dp),
        text = stringResource(id = CopyR.string.search_organization_no_results_heading),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
      )
      Text(
        modifier = Modifier.padding(top = 8.dp),
        text = stringResource(id = CopyR.string.search_organization_no_results_subheading),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.LabelsSecondary(),
        textAlign = TextAlign.Center,
      )
    }
  }
}

@Composable
private fun SearchTextField(
  modifier: Modifier = Modifier,
  loading: Boolean,
  textFieldState: TextFieldState,
  onSearch: (query: String) -> Unit,
) {
  val focusManager = LocalFocusManager.current
  val keyboardController = LocalSoftwareKeyboardController.current
  SearchBar(
    modifier = modifier,
    windowInsets = WindowInsets(0.dp),
    inputField = {
      SearchBarDefaults.InputField(
        query = textFieldState.text.toString(),
        placeholder = {
          Text(text = stringResource(CopyR.string.search_organization_search_placeholder), style = MaterialTheme.typography.bodyMedium)
        },
        onQueryChange = { query ->
          onSearch(query)
          textFieldState.edit { replace(0, length, query) }
        },
        onSearch = {
          focusManager.clearFocus()
          keyboardController?.hide()
        },
        expanded = false,
        onExpandedChange = { },
        leadingIcon = {
          if (loading) {
            SearchTextFieldProgressBar()
          } else {
            Icon(painter = painterResource(R.drawable.ic_search), contentDescription = stringResource(CopyR.string.common_search))
          }
        },
        trailingIcon = {
          if (textFieldState.text.isNotEmpty()) {
            Icon(
              modifier =
                Modifier.clickable {
                  onSearch("")
                  textFieldState.clearText()
                },
              painter = painterResource(R.drawable.ic_clear),
              contentDescription = stringResource(CopyR.string.common_clear),
            )
          }
        },
        colors =
          inputFieldColors(
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.SymbolsSecondary(),
            focusedTrailingIconColor = MaterialTheme.colorScheme.SymbolsSecondary(),
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.SymbolsSecondary(),
            focusedLeadingIconColor = MaterialTheme.colorScheme.SymbolsSecondary(),
            focusedContainerColor = MaterialTheme.colorScheme.BackgroundsSecondary(),
            unfocusedContainerColor = MaterialTheme.colorScheme.BackgroundsSecondary(),
            focusedPlaceholderColor = MaterialTheme.colorScheme.LabelsSecondary(),
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.LabelsSecondary(),
          ),
      )
    },
    expanded = false,
    onExpandedChange = {},
    content = {},
  )
}

@Composable
private fun SearchTextFieldProgressBar() {
  CircularProgressIndicator(
    modifier = Modifier.size(24.dp),
    strokeWidth = 2.dp,
    trackColor = MaterialTheme.colorScheme.CategoriesRijkslint().copy(alpha = 0.15f),
    color = MaterialTheme.colorScheme.CategoriesRijkslint(),
  )
}

@DefaultPreviews
@Composable
internal fun ManualLocalisationScreenEmptyPreview() {
  MgoTheme {
    ManualLocalisationScreenContent(
      viewState =
        ManualLocalisationScreenViewState(
          loading = false,
          organizations = listOf(),
          error = false,
        ),
      onSearch = {},
      onAddOrganization = {},
      onNavigateBack = null,
    )
  }
}

@DefaultPreviews
@Composable
internal fun ManualLocalisationScreenPreview() {
  MgoTheme {
    ManualLocalisationScreenContent(
      viewState =
        ManualLocalisationScreenViewState(
          loading = false,
          organizations = listOf(TEST_ORGANIZATION_UI_1, TEST_ORGANIZATION_UI_2, TEST_ORGANIZATION_UI_3),
          error = false,
        ),
      onSearch = {},
      onAddOrganization = {},
      onNavigateBack = null,
    )
  }
}
