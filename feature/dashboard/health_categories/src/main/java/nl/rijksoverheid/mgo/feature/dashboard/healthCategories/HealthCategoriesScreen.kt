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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.healthCategories.getString
import nl.rijksoverheid.mgo.component.mgo.MgoAutoScrollLazyColumn
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.StatesCritical
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_GROUP_HEALTH
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreenTestTag.DELETE_ORGANIZATION_BUTTON
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner.HealthCategoriesBannerError
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner.HealthCategoriesBannerLoading
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner.HealthCategoriesBannerState
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem.HealthCategoriesFavoriteCard
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem.HealthCategoriesListItem
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem.HealthCategoriesNoFavoriteCard
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

object HealthCategoriesScreenTestTag {
  const val LIST = "HealthCategoriesScreenList"
  const val DELETE_ORGANIZATION_BUTTON = "HealthCategoriesScreenDeleteOrganizationButton"
}

@Composable
fun HealthCategoriesScreen(
  appBarTitle: String,
  onNavigateRemoveOrganization: (organization: MgoOrganization) -> Unit,
  onNavigateToLocalisation: () -> Unit,
  onNavigateToHealthCategory: (
    category: HealthCategoryGroup.HealthCategory,
    organization: MgoOrganization?,
  ) -> Unit,
  organization: MgoOrganization? = null,
  onShowBottomSheet: (() -> Unit)? = null,
  onNavigateBack: (() -> Unit)? = null,
) {
  val viewModel = hiltViewModel<HealthCategoriesScreenViewModel>()
  val viewState: HealthCategoriesScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()

  HealthCategoriesScreenContent(
    appBarTitle = appBarTitle,
    viewState = viewState,
    onNavigateBack = onNavigateBack,
    onClickAddProvider = onNavigateToLocalisation,
    onClickListItem = { category -> onNavigateToHealthCategory(category, organization) },
    onClickRemoveOrganization = onNavigateRemoveOrganization,
    onShowBottomSheet = onShowBottomSheet,
    organization = organization,
    onRetry = {
      viewModel.retry(failedOnly = true)
    },
  )
}

@Composable
private fun HealthCategoriesScreenContent(
  appBarTitle: String,
  viewState: HealthCategoriesScreenViewState,
  onClickListItem: (category: HealthCategoryGroup.HealthCategory) -> Unit,
  onClickAddProvider: () -> Unit,
  onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
  onRetry: () -> Unit,
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
          modifier =
            Modifier
              .weight(1f)
              .testTag(HealthCategoriesScreenTestTag.LIST),
          contentPadding = PaddingValues(16.dp),
          state = lazyListState,
        ) { canScroll ->
          if (viewState.providers.isEmpty()) {
            NoProviders(canScroll)
          } else {
            // If we are on the overview screen, we split the view into favorites and non favorites. If looking at a specific organization,
            // we just show all the categories.
            WithProviders(
              onClickListItem = onClickListItem,
              onClickRemoveOrganization = onClickRemoveOrganization,
              onClickAddFavorite = { onShowBottomSheet?.invoke() },
              organization = organization,
              groups = viewState.groups,
              favorites = viewState.favorites,
              banner = viewState.banner,
              onRetry = onRetry,
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
          color = MaterialTheme.colorScheme.LabelsSecondary(),
          textAlign = TextAlign.Center,
        )
      }
    }
  }
}

@Suppress("ktlint:standard:function-naming")
private fun LazyListScope.WithProviders(
  onClickAddFavorite: () -> Unit,
  onClickListItem: (category: HealthCategoryGroup.HealthCategory) -> Unit,
  onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
  organization: MgoOrganization? = null,
  groups: List<HealthCategoryGroup>,
  favorites: List<HealthCategoryGroup.HealthCategory>,
  banner: HealthCategoriesBannerState?,
  onRetry: () -> Unit,
) {
  if (organization == null) {
    item(key = banner.hashCode()) {
      when (banner) {
        null -> {}
        HealthCategoriesBannerState.Loading -> HealthCategoriesBannerLoading(modifier = Modifier.padding(bottom = 32.dp).animateItem())
        is HealthCategoriesBannerState.Error.ServerError ->
          HealthCategoriesBannerError(
            modifier = Modifier.padding(bottom = 32.dp).animateItem(),
            state = HealthCategoriesBannerState.Error.ServerError(banner.partial),
            onClickRetry = onRetry,
          )
        is HealthCategoriesBannerState.Error.UserError ->
          HealthCategoriesBannerError(
            modifier = Modifier.padding(bottom = 32.dp).animateItem(),
            state = HealthCategoriesBannerState.Error.UserError(banner.partial),
            onClickRetry = onRetry,
          )
      }
    }

    if (favorites.isEmpty()) {
      item(key = "favorites_empty") {
        Column(modifier = Modifier.animateItem()) {
          Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(CopyR.string.overview_favorites_heading),
            style = MaterialTheme.typography.headlineSmall,
          )

          HealthCategoriesNoFavoriteCard(
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
            onClickAddFavorite = onClickAddFavorite,
          )
        }
      }
    } else {
      item(key = "favorites") {
        Column(modifier = Modifier.animateItem()) {
          Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(CopyR.string.overview_favorites_heading),
            style = MaterialTheme.typography.headlineSmall,
          )

          FlowRow(modifier = Modifier.padding(bottom = 32.dp), horizontalArrangement = spacedBy(8.dp), verticalArrangement = spacedBy(8.dp)) {
            favorites.forEach { favorite ->
              HealthCategoriesFavoriteCard(
                category = favorite,
                onClick = { onClickListItem(favorite) },
              )
            }
          }
        }
      }
    }
  }

  for (group in groups) {
    item(key = group.id) {
      Column(modifier = Modifier.animateItem()) {
        if (group.categories.isNotEmpty()) {
          Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = LocalContext.current.getString(group.heading),
            style = MaterialTheme.typography.headlineSmall,
          )
        }

        MgoCard {
          group.categories.forEachIndexed { index, category ->
            HealthCategoriesListItem(
              modifier = Modifier.clickable { onClickListItem(category) },
              category = category,
              filterOrganization = organization,
              hasDivider = index != group.categories.lastIndex,
            )
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(32.dp))
    }
  }

  if (organization != null) {
    item {
      Column(
        modifier =
          Modifier
            .fillMaxWidth()
            .testTag(DELETE_ORGANIZATION_BUTTON),
      ) {
        TextButton(
          modifier =
            Modifier
              .align(Alignment.CenterHorizontally),
          onClick = { onClickRemoveOrganization(organization) },
        ) {
          Text(
            text = stringResource(id = CopyR.string.organizations_remove_organization),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.StatesCritical(),
          )
        }
      }
    }
  }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenNoProvidersPreview() {
  MgoTheme {
    HealthCategoriesScreenContent(
      appBarTitle = stringResource(CopyR.string.overview_heading),
      viewState =
        HealthCategoriesScreenViewState(
          name = "",
          providers = listOf(),
          automaticLocalisationEnabled = false,
          groups = listOf(),
          favorites = listOf(),
          banner = null,
        ),
      onNavigateBack = {},
      onClickAddProvider = {},
      onClickListItem = {},
      onClickRemoveOrganization = {},
      onShowBottomSheet = {},
      onRetry = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenWithProvidersPreview() {
  MgoTheme {
    HealthCategoriesScreenContent(
      appBarTitle = stringResource(CopyR.string.overview_heading),
      viewState =
        HealthCategoriesScreenViewState(
          name = "",
          providers = listOf(TEST_MGO_ORGANIZATION),
          automaticLocalisationEnabled = false,
          groups = listOf(TEST_HEALTH_CATEGORY_GROUP_HEALTH),
          favorites = listOf(),
          banner = null,
        ),
      onNavigateBack = {},
      onClickAddProvider = {},
      onClickListItem = {},
      onClickRemoveOrganization = {},
      onShowBottomSheet = {},
      onRetry = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenWithProvidersAndFavoritesPreview() {
  MgoTheme {
    HealthCategoriesScreenContent(
      appBarTitle = stringResource(CopyR.string.overview_heading),
      viewState =
        HealthCategoriesScreenViewState(
          name = "",
          providers = listOf(TEST_MGO_ORGANIZATION),
          automaticLocalisationEnabled = false,
          groups = listOf(TEST_HEALTH_CATEGORY_GROUP_HEALTH.copy(categories = listOf(TEST_HEALTH_CATEGORY_GROUP_HEALTH.categories[0]))),
          favorites = listOf(TEST_HEALTH_CATEGORY_GROUP_HEALTH.categories[1]),
          banner = null,
        ),
      onNavigateBack = {},
      onClickAddProvider = {},
      onClickListItem = {},
      onClickRemoveOrganization = {},
      onShowBottomSheet = {},
      onRetry = {},
    )
  }
}
