package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.healthCareCategory.getIcon
import nl.rijksoverheid.mgo.component.healthCareCategory.getIconColor
import nl.rijksoverheid.mgo.component.healthCareCategory.getTitle
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
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.TEST_HEALTH_CARE_CATEGORIES
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreenTestTag.DELETE_ORGANIZATION_BUTTON
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem.HealthCategoriesFavoriteCard
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem.HealthCategoriesListItem
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem.HealthCategoriesNoFavoriteCard
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
 * @param onShowBottomSheet If not null, shows an bottom sheet where you can edit the overview screen.
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
  onShowBottomSheet: (() -> Unit)? = null,
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
    onShowBottomSheet = onShowBottomSheet,
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
  onShowBottomSheet: (() -> Unit)? = null,
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
        actions = {
          if (viewState.providers.isNotEmpty() && onShowBottomSheet != null) {
            IconButton(onShowBottomSheet) {
              Icon(Icons.Default.MoreHoriz, null)
            }
          }
        },
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
            // If we are on the overview screen, we split the view into favorites and non favorites. If looking at a specific organization,
            // we just show all the categories.
            val categories = if (organization == null) viewState.categories.filter { category -> category.favoritePosition == -1 } else viewState.categories
            WithProviders(
              subHeading = subHeading,
              onClickListItem = onClickListItem,
              onClickRemoveOrganization = onClickRemoveOrganization,
              onClickAddFavorite = { onShowBottomSheet?.invoke() },
              organization = organization,
              categories = categories,
              favorites = viewState.favorites,
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
  onClickAddFavorite: () -> Unit,
  onClickListItem: (category: HealthCareCategoryId) -> Unit,
  onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
  organization: MgoOrganization? = null,
  categories: List<HealthCareCategory>,
  favorites: List<HealthCareCategory>,
) {
  if (organization == null) {
    item {
      Text(
        modifier = Modifier.padding(bottom = 12.dp),
        text = stringResource(CopyR.string.overview_favorites_heading),
        style = MaterialTheme.typography.headlineSmall,
      )
    }

    if (favorites.isEmpty()) {
      item {
        HealthCategoriesNoFavoriteCard(
          modifier = Modifier.fillMaxWidth(),
          onClickAddFavorite = onClickAddFavorite,
        )
      }
    } else {
      item {
        FlowRow(horizontalArrangement = spacedBy(8.dp), verticalArrangement = spacedBy(8.dp)) {
          favorites.forEach { favorite ->
            HealthCategoriesFavoriteCard(
              category = favorite.id,
              onClick = { onClickListItem(favorite.id) },
            )
          }
        }
      }
    }
  }

  if (categories.isNotEmpty()) {
    if (organization == null) {
      item {
        Text(modifier = Modifier.padding(top = 32.dp, bottom = 8.dp), text = "Alle categorieën", style = MaterialTheme.typography.headlineSmall)
      }
    } else {
      item {
        Text(
          modifier = Modifier.padding(bottom = 8.dp),
          text = subHeading,
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }

    items(categories.size) { position ->
      HealthCategoriesListItemCard(
        position =
          when {
            categories.size == 1 -> HealthCategoriesListItemCardPosition.SINGLE_ITEM
            position == 0 -> HealthCategoriesListItemCardPosition.TOP
            position == categories.lastIndex -> HealthCategoriesListItemCardPosition.BOTTOM
            else -> HealthCategoriesListItemCardPosition.CENTER
          },
        category = categories[position].id,
        onClickListItem = onClickListItem,
        filterOrganization = organization,
      )
    }
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
  SINGLE_ITEM,
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

      HealthCategoriesListItemCardPosition.SINGLE_ITEM -> {
        RoundedCornerShape(
          topStart = 16.dp,
          topEnd = 16.dp,
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
      hasDivider = position != HealthCategoriesListItemCardPosition.BOTTOM && position != HealthCategoriesListItemCardPosition.SINGLE_ITEM,
    )
  }
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
          favorites = listOf(),
        ),
      onNavigateBack = {},
      onClickAddProvider = {},
      onClickListItem = {},
      onClickRemoveOrganization = {},
      onShowBottomSheet = {},
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
          favorites = listOf(),
        ),
      onNavigateBack = {},
      onClickAddProvider = {},
      onClickListItem = {},
      onClickRemoveOrganization = {},
      onShowBottomSheet = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenWithProvidersAndFavoritesPreview() {
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
          favorites = listOf(TEST_HEALTH_CARE_CATEGORIES.first()),
        ),
      onNavigateBack = {},
      onClickAddProvider = {},
      onClickListItem = {},
      onClickRemoveOrganization = {},
      onShowBottomSheet = {},
    )
  }
}
