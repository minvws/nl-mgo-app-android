package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import getStringResourceByName
import nl.rijksoverheid.mgo.component.mgo.MgoAutoScrollLazyColumn
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoButton
import nl.rijksoverheid.mgo.component.mgo.MgoButtonTheme
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.supportAllergies
import nl.rijksoverheid.mgo.component.theme.supportContacts
import nl.rijksoverheid.mgo.component.theme.supportDevice
import nl.rijksoverheid.mgo.component.theme.supportDocuments
import nl.rijksoverheid.mgo.component.theme.supportFunctional
import nl.rijksoverheid.mgo.component.theme.supportLaboratory
import nl.rijksoverheid.mgo.component.theme.supportLifestyle
import nl.rijksoverheid.mgo.component.theme.supportMedication
import nl.rijksoverheid.mgo.component.theme.supportPayer
import nl.rijksoverheid.mgo.component.theme.supportPersonal
import nl.rijksoverheid.mgo.component.theme.supportProblems
import nl.rijksoverheid.mgo.component.theme.supportProcedures
import nl.rijksoverheid.mgo.component.theme.supportTreatment
import nl.rijksoverheid.mgo.component.theme.supportVaccinations
import nl.rijksoverheid.mgo.component.theme.supportVitals
import nl.rijksoverheid.mgo.component.theme.supportWarning
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreenTestTag.DELETE_ORGANIZATION_BUTTON
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem.HealthCategoriesListItem
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

object HealthCategoriesScreenTestTag {
  const val LIST = "HealthCategoriesScreenList"
  const val DELETE_ORGANIZATION_BUTTON = "HealthCategoriesScreenDeleteOrganizationButton"
}

/**
 * Composable that shows a screen with a list of health categories. These health categories are populated with either health cara data
 * for all added health providers, or just a single one.
 *
 * @param appBarTitle The title of the app bar.
 * @param subHeading Text under the app bar that contains a small explanation about this screen.
 * @param onNavigateRemoveOrganization Called when requested to navigate to the screen where you can remove an organization.
 * @param onNavigateToLocalisation Called when requested to navigate to the screen where you can search for organizations.
 * @param onNavigateToHealthCategory Called when requested to navigate to the screen where you can view health care data.
 * @param organization If not null, will only show only health care data for this organization. If null will show for all added
 * organizations.
 * @param onNavigateBack Called when requested to navigate back.
 */
@Composable
fun HealthCategoriesScreen(
  appBarTitle: String,
  subHeading: String,
  onNavigateRemoveOrganization: (organization: MgoOrganization) -> Unit,
  onNavigateToLocalisation: () -> Unit,
  onNavigateToHealthCategory: (category: HealthCareCategory, organization: MgoOrganization?) -> Unit,
  organization: MgoOrganization? = null,
  onNavigateBack: (() -> Unit)? = null,
) {
  val viewModel = hiltViewModel<HealthCategoriesScreenViewModel>()
  val viewState: HealthCategoriesScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()
  HealthCategoriesScreenContent(
    appBarTitle = appBarTitle,
    subHeading = subHeading,
    viewState = viewState,
    onNavigateBack = onNavigateBack,
    onClickAddProvider = onNavigateToLocalisation,
    onClickListItem = { category -> onNavigateToHealthCategory(category, organization) },
    onClickRemoveOrganization = onNavigateRemoveOrganization,
    organization = organization,
  )
}

@Composable
private fun HealthCategoriesScreenContent(
  appBarTitle: String,
  subHeading: String,
  viewState: HealthCategoriesScreenViewState,
  onClickListItem: (category: HealthCareCategory) -> Unit,
  onClickAddProvider: () -> Unit,
  onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
  organization: MgoOrganization? = null,
  onNavigateBack: (() -> Unit)? = null,
) {
  val lazyListState = rememberLazyListState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(lazyListState.canScrollForward, lazyListState.canScrollBackward)
  val primaryButton =
    when {
      viewState.providers.isEmpty() -> {
        if (viewState.automaticLocalisationEnabled) {
          MgoBottomButton(
            text = stringResource(id = CopyR.string.common_search_organizations),
            onClick = onClickAddProvider,
          )
        } else {
          MgoBottomButton(
            text = stringResource(id = CopyR.string.common_add_organizations),
            onClick = onClickAddProvider,
          )
        }
      }

      else -> {
        null
      }
    }

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    contentWindowInsets = WindowInsets.statusBars,
    topBar = {
      MgoLargeTopAppBar(
        title = appBarTitle,
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
      )
    },
    content = { contentPadding ->
      Column(
        modifier = Modifier.padding(contentPadding),
      ) {
        MgoAutoScrollLazyColumn(
          modifier = Modifier.weight(1f).testTag(HealthCategoriesScreenTestTag.LIST),
          contentPadding = PaddingValues(16.dp),
          state = lazyListState,
        ) { canScroll ->
          if (viewState.providers.isEmpty()) {
            NoProviders(canScroll)
          } else {
            WithProviders(
              subHeading = subHeading,
              onClickListItem = onClickListItem,
              onClickRemoveOrganization = onClickRemoveOrganization,
              organization = organization,
            )
          }
        }

        if (primaryButton != null) {
          MgoBottomButtons(
            primaryButton = primaryButton,
            isElevated = lazyListState.canScrollForward,
          )
        }
      }
    },
  )
}

@Suppress("ktlint:standard:function-naming")
private fun LazyListScope.NoProviders(canScroll: Boolean) {
  item {
    Box(modifier = if (canScroll) Modifier else Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
      Column {
        Image(
          modifier =
            Modifier
              .fillMaxWidth()
              .height(156.dp),
          painter = painterResource(id = R.drawable.illustration_overview_empty),
          contentDescription = null,
        )
        Text(
          modifier =
            Modifier
              .fillMaxWidth()
              .padding(top = 24.dp),
          text = stringResource(id = CopyR.string.common_no_organizations_heading),
          style = MaterialTheme.typography.headlineSmall,
          textAlign = TextAlign.Center,
        )
        Text(
          modifier =
            Modifier
              .fillMaxWidth()
              .padding(top = 8.dp),
          text = stringResource(id = CopyR.string.common_no_organizations_subheading),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.contentSecondary(),
          textAlign = TextAlign.Center,
        )
      }
    }
  }
}

@Suppress("ktlint:standard:function-naming")
private fun LazyListScope.WithProviders(
  subHeading: String,
  onClickListItem: (category: HealthCareCategory) -> Unit,
  onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
  organization: MgoOrganization? = null,
) {
  item {
    Text(
      modifier = Modifier.padding(bottom = 8.dp),
      text = subHeading,
      style = MaterialTheme.typography.bodyMedium,
    )
  }

  items(HealthCareCategory.entries.size) { position ->
    HealthCategoriesListItemCard(
      position =
        when (position) {
          0 -> HealthCategoriesListItemCardPosition.TOP
          HealthCareCategory.entries.lastIndex -> HealthCategoriesListItemCardPosition.BOTTOM
          else -> HealthCategoriesListItemCardPosition.CENTER
        },
      category = HealthCareCategory.entries[position],
      onClickListItem = onClickListItem,
      filterOrganization = organization,
    )
  }

  if (organization != null) {
    item {
      Column(modifier = Modifier.fillMaxWidth().testTag(DELETE_ORGANIZATION_BUTTON)) {
        MgoButton(
          modifier =
            Modifier
              .padding(bottom = 16.dp)
              .align(Alignment.CenterHorizontally),
          buttonText = stringResource(id = CopyR.string.organizations_remove_organization),
          onClick = {
            onClickRemoveOrganization(organization)
          },
          buttonTheme = MgoButtonTheme.TERTIARY_NEGATIVE,
        )
      }
    }
  } else {
    item {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

private enum class HealthCategoriesListItemCardPosition {
  TOP,
  CENTER,
  BOTTOM,
}

@Composable
private fun HealthCategoriesListItemCard(
  position: HealthCategoriesListItemCardPosition,
  category: HealthCareCategory,
  onClickListItem: (category: HealthCareCategory) -> Unit,
  filterOrganization: MgoOrganization?,
) {
  val shape =
    when (position) {
      HealthCategoriesListItemCardPosition.TOP -> {
        RoundedCornerShape(
          topStart = 16.dp,
          topEnd = 16.dp,
          bottomStart = 0.dp,
          bottomEnd = 0.dp,
        )
      }

      HealthCategoriesListItemCardPosition.CENTER -> {
        RoundedCornerShape(
          topStart = 0.dp,
          topEnd = 0.dp,
          bottomStart = 0.dp,
          bottomEnd = 0.dp,
        )
      }

      HealthCategoriesListItemCardPosition.BOTTOM -> {
        RoundedCornerShape(
          topStart = 0.dp,
          topEnd = 0.dp,
          bottomStart = 16.dp,
          bottomEnd = 16.dp,
        )
      }
    }

  MgoCard(shape = shape) {
    HealthCategoriesListItem(
      modifier = Modifier.clickable { onClickListItem(category) },
      icon = category.getIcon(),
      title = category.getTitle(),
      iconColor = category.getIconColor(),
      category = category,
      filterOrganization = filterOrganization,
    )
  }
}

@Composable
@StringRes
private fun HealthCareCategory.getTitle(): Int {
  val stringResource = LocalContext.current.getStringResourceByName("hc_$id.heading")
  if (stringResource == 0) {
    return CopyR.string.common_unknown
  }
  return stringResource
}

@DrawableRes
private fun HealthCareCategory.getIcon(): Int =
  when (this) {
    HealthCareCategory.MEDICATIONS -> R.drawable.ic_medication
    HealthCareCategory.MEASUREMENTS -> R.drawable.ic_measurements
    HealthCareCategory.LAB_RESULTS -> R.drawable.ic_labresults
    HealthCareCategory.ALLERGIES -> R.drawable.ic_allergies
    HealthCareCategory.TREATMENTS -> R.drawable.ic_treatments
    HealthCareCategory.APPOINTMENTS -> R.drawable.ic_appointments
    HealthCareCategory.VACCINATIONS -> R.drawable.ic_vaccinations
    HealthCareCategory.DOCUMENTS -> R.drawable.ic_documents
    HealthCareCategory.COMPLAINTS -> R.drawable.ic_complaints
    HealthCareCategory.PATIENT -> R.drawable.ic_patient
    HealthCareCategory.ALERTS -> R.drawable.ic_alerts
    HealthCareCategory.PAYMENT -> R.drawable.ic_payment
    HealthCareCategory.PLANS -> R.drawable.ic_plans
    HealthCareCategory.DEVICES -> R.drawable.ic_devices
    HealthCareCategory.MENTAL -> R.drawable.ic_mental
    HealthCareCategory.LIFESTYLE -> R.drawable.ic_lifestyle
  }

@Composable
private fun HealthCareCategory.getIconColor(): Color =
  when (this) {
    HealthCareCategory.MEDICATIONS -> MaterialTheme.colorScheme.supportMedication()
    HealthCareCategory.MEASUREMENTS -> MaterialTheme.colorScheme.supportVitals()
    HealthCareCategory.LAB_RESULTS -> MaterialTheme.colorScheme.supportLaboratory()
    HealthCareCategory.ALLERGIES -> MaterialTheme.colorScheme.supportAllergies()
    HealthCareCategory.TREATMENTS -> MaterialTheme.colorScheme.supportTreatment()
    HealthCareCategory.APPOINTMENTS -> MaterialTheme.colorScheme.supportContacts()
    HealthCareCategory.VACCINATIONS -> MaterialTheme.colorScheme.supportVaccinations()
    HealthCareCategory.DOCUMENTS -> MaterialTheme.colorScheme.supportDocuments()
    HealthCareCategory.COMPLAINTS -> MaterialTheme.colorScheme.supportProblems()
    HealthCareCategory.PATIENT -> MaterialTheme.colorScheme.supportPersonal()
    HealthCareCategory.ALERTS -> MaterialTheme.colorScheme.supportWarning()
    HealthCareCategory.PAYMENT -> MaterialTheme.colorScheme.supportPayer()
    HealthCareCategory.PLANS -> MaterialTheme.colorScheme.supportProcedures()
    HealthCareCategory.DEVICES -> MaterialTheme.colorScheme.supportDevice()
    HealthCareCategory.MENTAL -> MaterialTheme.colorScheme.supportFunctional()
    HealthCareCategory.LIFESTYLE -> MaterialTheme.colorScheme.supportLifestyle()
  }

@DefaultPreviews
@Composable
internal fun OverviewScreenNoProvidersPreview() {
  MgoTheme {
    HealthCategoriesScreenContent(
      appBarTitle = stringResource(CopyR.string.overview_heading),
      subHeading = stringResource(CopyR.string.overview_subheading),
      viewState = HealthCategoriesScreenViewState(name = "", providers = listOf(), automaticLocalisationEnabled = false),
      onNavigateBack = {},
      onClickAddProvider = {},
      onClickListItem = {},
      onClickRemoveOrganization = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenWithProvidersPreview() {
  MgoTheme {
    HealthCategoriesScreenContent(
      appBarTitle = stringResource(CopyR.string.overview_heading),
      subHeading = stringResource(CopyR.string.overview_subheading),
      viewState =
        HealthCategoriesScreenViewState(
          name = "",
          providers = listOf(TEST_MGO_ORGANIZATION),
          automaticLocalisationEnabled = false,
        ),
      onNavigateBack = {},
      onClickAddProvider = {},
      onClickListItem = {},
      onClickRemoveOrganization = {},
    )
  }
}
