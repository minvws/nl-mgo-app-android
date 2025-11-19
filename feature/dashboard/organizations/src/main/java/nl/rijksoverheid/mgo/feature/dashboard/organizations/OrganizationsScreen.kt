package nl.rijksoverheid.mgo.feature.dashboard.organizations

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import nl.rijksoverheid.mgo.component.theme.SeperatorsPrimary
import nl.rijksoverheid.mgo.component.theme.SymbolsSecondary
import nl.rijksoverheid.mgo.feature.organizations.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

object OrganizationsScreenTestTag {
  const val ORGANIZATION_CARD = "OrganizationsScreenOrganizationCard"
  const val ADD_ORGANIZATION_BUTTON = "OrganizationsScreenAddOrganizationButton"
  const val EMPTY_STATE = "OrganizationsScreenEmptyState"
}

@Composable
fun OrganizationsScreen(
  onNavigateToHealthCategories: (organization: MgoOrganization) -> Unit,
  onNavigateToLocalisation: () -> Unit,
) {
  val viewModel: OrganizationsViewModel = hiltViewModel()
  val viewState: OrganizationsViewState by viewModel.viewState.collectAsStateWithLifecycle()
  OrganizationsScreenContent(
    viewState = viewState,
    onClickOrganization = onNavigateToHealthCategories,
    onClickAddProvider = onNavigateToLocalisation,
  )
}

@Composable
private fun OrganizationsScreenContent(
  viewState: OrganizationsViewState,
  onClickOrganization: (organization: MgoOrganization) -> Unit,
  onClickAddProvider: () -> Unit,
) {
  val lazyListState = rememberLazyListState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(lazyListState.canScrollForward, lazyListState.canScrollBackward)

  val primaryButton =
    when {
      viewState.organizations.isEmpty() -> {
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
    topBar = {
      MgoLargeTopAppBar(
        title = stringResource(CopyR.string.organizations_heading),
        scrollBehavior = scrollBehavior,
      )
    },
    content = { contentPadding ->
      Column(
        modifier = Modifier.padding(contentPadding),
      ) {
        MgoAutoScrollLazyColumn(
          modifier = Modifier.weight(1f),
          contentPadding = PaddingValues(16.dp),
          state = lazyListState,
        ) { canScroll ->
          if (viewState.organizations.isEmpty()) {
            NoOrganizations(canScroll)
          } else {
            WithOrganizations(
              organizations = viewState.organizations,
              onClickOrganization = onClickOrganization,
              onClickAddProvider = onClickAddProvider,
              automaticLocalisationEnabled = viewState.automaticLocalisationEnabled,
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
private fun LazyListScope.NoOrganizations(canScroll: Boolean) {
  item {
    Column(
      modifier =
        if (canScroll) {
          Modifier.testTag(
            OrganizationsScreenTestTag.EMPTY_STATE,
          )
        } else {
          Modifier.fillParentMaxSize().testTag(OrganizationsScreenTestTag.EMPTY_STATE)
        },
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        modifier =
          Modifier
            .fillMaxWidth()
            .height(156.dp),
        painter = painterResource(id = R.drawable.illustration_organizations_empty),
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

@Suppress("ktlint:standard:function-naming")
private fun LazyListScope.WithOrganizations(
  organizations: List<MgoOrganization>,
  automaticLocalisationEnabled: Boolean,
  onClickOrganization: (organization: MgoOrganization) -> Unit,
  onClickAddProvider: () -> Unit,
) {
  items(organizations.size) { position ->
    val organization = organizations[position]

    OrganizationCard(
      modifier = Modifier.fillMaxWidth(),
      position =
        when {
          organizations.size == 1 -> OrganizationCardPosition.SINGLE
          position == 0 -> OrganizationCardPosition.TOP
          position == organizations.lastIndex -> OrganizationCardPosition.BOTTOM
          else -> OrganizationCardPosition.CENTER
        },
      organization = organization,
      hasDivider = position != organizations.lastIndex,
      onClick = { onClickOrganization(organization) },
    )
  }

  item {
    MgoCard(
      modifier =
        Modifier
          .padding(vertical = 16.dp)
          .testTag(OrganizationsScreenTestTag.ADD_ORGANIZATION_BUTTON),
    ) {
      Row(
        modifier =
          Modifier
            .fillMaxWidth()
            .clickable { onClickAddProvider() }
            .padding(16.dp),
      ) {
        val stringResource =
          if (automaticLocalisationEnabled) CopyR.string.common_search_organizations else CopyR.string.common_add_organizations
        Text(
          text = stringResource(id = stringResource),
          style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
          modifier = Modifier.padding(start = 8.dp),
          painter = painterResource(id = R.drawable.ic_add_organization),
          tint = MaterialTheme.colorScheme.SymbolsSecondary(),
          contentDescription = null,
        )
      }
    }
  }
}

private enum class OrganizationCardPosition {
  TOP,
  CENTER,
  BOTTOM,
  SINGLE,
}

@Composable
private fun OrganizationCard(
  position: OrganizationCardPosition,
  organization: MgoOrganization,
  onClick: () -> Unit,
  hasDivider: Boolean,
  modifier: Modifier = Modifier,
) {
  val shape =
    when (position) {
      OrganizationCardPosition.TOP -> {
        RoundedCornerShape(
          topStart = 16.dp,
          topEnd = 16.dp,
          bottomStart = 0.dp,
          bottomEnd = 0.dp,
        )
      }

      OrganizationCardPosition.CENTER -> {
        RoundedCornerShape(
          topStart = 0.dp,
          topEnd = 0.dp,
          bottomStart = 0.dp,
          bottomEnd = 0.dp,
        )
      }

      OrganizationCardPosition.BOTTOM -> {
        RoundedCornerShape(
          topStart = 0.dp,
          topEnd = 0.dp,
          bottomStart = 16.dp,
          bottomEnd = 16.dp,
        )
      }
      OrganizationCardPosition.SINGLE -> {
        RoundedCornerShape(
          topStart = 16.dp,
          topEnd = 16.dp,
          bottomStart = 16.dp,
          bottomEnd = 16.dp,
        )
      }
    }

  MgoCard(modifier = modifier.testTag(OrganizationsScreenTestTag.ORGANIZATION_CARD), shape = shape) {
    Column(modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
      Text(
        modifier = Modifier.padding(16.dp),
        text = organization.name,
        style = MaterialTheme.typography.bodyMedium,
      )
      if (hasDivider) {
        HorizontalDivider(
          modifier =
            Modifier
              .fillMaxWidth()
              .height(0.33.dp)
              .padding(start = 16.dp),
          color = MaterialTheme.colorScheme.SeperatorsPrimary(),
        )
      }
    }
  }
}

@DefaultPreviews
@Composable
internal fun OrganizationsScreenNoOrganizationsPreview() {
  MgoTheme {
    OrganizationsScreenContent(
      viewState = OrganizationsViewState(organizations = listOf(), automaticLocalisationEnabled = false),
      onClickOrganization = {},
      onClickAddProvider = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun OrganizationsScreenWithOrganizationsPreview() {
  MgoTheme {
    OrganizationsScreenContent(
      viewState =
        OrganizationsViewState(
          organizations =
            listOf(
              TEST_MGO_ORGANIZATION
                .copy(name = "Streekziekenhuis Willem Alexander"),
              TEST_MGO_ORGANIZATION
                .copy(name = "Huisartsenpraktijk De Haven"),
              TEST_MGO_ORGANIZATION
                .copy(name = "Fysiotherapie Centrum"),
              TEST_MGO_ORGANIZATION
                .copy(name = "Tandartsenpraktijk Tandje Erbij"),
              TEST_MGO_ORGANIZATION
                .copy(name = "Apotheek de Pillendoos"),
            ),
          automaticLocalisationEnabled = false,
        ),
      onClickOrganization = {},
      onClickAddProvider = {},
    )
  }
}
