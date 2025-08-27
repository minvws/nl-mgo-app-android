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
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.TEST_HEALTH_CARE_CATEGORIES
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
  onNavigateToHealthCategory: (category: HealthCareCategoryId, organization: MgoOrganization?) -> Unit,
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
  onClickListItem: (category: HealthCareCategoryId) -> Unit,
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
              categories = viewState.categories,
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
  onClickListItem: (category: HealthCareCategoryId) -> Unit,
  onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
  organization: MgoOrganization? = null,
  categories: List<HealthCareCategory>,
) {
  item {
    Text(
      modifier = Modifier.padding(bottom = 8.dp),
      text = subHeading,
      style = MaterialTheme.typography.bodyMedium,
    )
  }

  items(categories.size) { position ->
    HealthCategoriesListItemCard(
      position =
        when (position) {
          0 -> HealthCategoriesListItemCardPosition.TOP
          HealthCareCategoryId.entries.lastIndex -> HealthCategoriesListItemCardPosition.BOTTOM
          else -> HealthCategoriesListItemCardPosition.CENTER
        },
      category = categories[position].id,
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
  category: HealthCareCategoryId,
  onClickListItem: (category: HealthCareCategoryId) -> Unit,
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
private fun HealthCareCategoryId.getTitle(): Int {
  val stringResource = LocalContext.current.getStringResourceByName("hc_$id.heading")
  if (stringResource == 0) {
    return CopyR.string.common_unknown
  }
  return stringResource
}

@DrawableRes
private fun HealthCareCategoryId.getIcon(): Int =
  when (this) {
    HealthCareCategoryId.MEDICATIONS -> R.drawable.ic_medication
    HealthCareCategoryId.MEASUREMENTS -> R.drawable.ic_measurements
    HealthCareCategoryId.LAB_RESULTS -> R.drawable.ic_labresults
    HealthCareCategoryId.ALLERGIES -> R.drawable.ic_allergies
    HealthCareCategoryId.TREATMENTS -> R.drawable.ic_treatments
    HealthCareCategoryId.APPOINTMENTS -> R.drawable.ic_appointments
    HealthCareCategoryId.VACCINATIONS -> R.drawable.ic_vaccinations
    HealthCareCategoryId.DOCUMENTS -> R.drawable.ic_documents
    HealthCareCategoryId.COMPLAINTS -> R.drawable.ic_complaints
    HealthCareCategoryId.PATIENT -> R.drawable.ic_patient
    HealthCareCategoryId.ALERTS -> R.drawable.ic_alerts
    HealthCareCategoryId.PAYMENT -> R.drawable.ic_payment
    HealthCareCategoryId.PLANS -> R.drawable.ic_plans
    HealthCareCategoryId.DEVICES -> R.drawable.ic_devices
    HealthCareCategoryId.MENTAL -> R.drawable.ic_mental
    HealthCareCategoryId.LIFESTYLE -> R.drawable.ic_lifestyle
  }

@Composable
private fun HealthCareCategoryId.getIconColor(): Color =
  when (this) {
    HealthCareCategoryId.MEDICATIONS -> MaterialTheme.colorScheme.supportMedication()
    HealthCareCategoryId.MEASUREMENTS -> MaterialTheme.colorScheme.supportVitals()
    HealthCareCategoryId.LAB_RESULTS -> MaterialTheme.colorScheme.supportLaboratory()
    HealthCareCategoryId.ALLERGIES -> MaterialTheme.colorScheme.supportAllergies()
    HealthCareCategoryId.TREATMENTS -> MaterialTheme.colorScheme.supportTreatment()
    HealthCareCategoryId.APPOINTMENTS -> MaterialTheme.colorScheme.supportContacts()
    HealthCareCategoryId.VACCINATIONS -> MaterialTheme.colorScheme.supportVaccinations()
    HealthCareCategoryId.DOCUMENTS -> MaterialTheme.colorScheme.supportDocuments()
    HealthCareCategoryId.COMPLAINTS -> MaterialTheme.colorScheme.supportProblems()
    HealthCareCategoryId.PATIENT -> MaterialTheme.colorScheme.supportPersonal()
    HealthCareCategoryId.ALERTS -> MaterialTheme.colorScheme.supportWarning()
    HealthCareCategoryId.PAYMENT -> MaterialTheme.colorScheme.supportPayer()
    HealthCareCategoryId.PLANS -> MaterialTheme.colorScheme.supportProcedures()
    HealthCareCategoryId.DEVICES -> MaterialTheme.colorScheme.supportDevice()
    HealthCareCategoryId.MENTAL -> MaterialTheme.colorScheme.supportFunctional()
    HealthCareCategoryId.LIFESTYLE -> MaterialTheme.colorScheme.supportLifestyle()
  }

@DefaultPreviews
@Composable
internal fun OverviewScreenNoProvidersPreview() {
  MgoTheme {
    HealthCategoriesScreenContent(
      appBarTitle = stringResource(CopyR.string.overview_heading),
      subHeading = stringResource(CopyR.string.overview_subheading),
      viewState =
        HealthCategoriesScreenViewState(
          name = "",
          providers = listOf(),
          automaticLocalisationEnabled = false,
          categories = TEST_HEALTH_CARE_CATEGORIES,
        ),
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
          categories = TEST_HEALTH_CARE_CATEGORIES,
        ),
      onNavigateBack = {},
      onClickAddProvider = {},
      onClickListItem = {},
      onClickRemoveOrganization = {},
    )
  }
}
