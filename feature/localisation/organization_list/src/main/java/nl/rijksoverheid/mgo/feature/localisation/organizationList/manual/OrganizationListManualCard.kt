package nl.rijksoverheid.mgo.feature.localisation.organizationList.manual

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.StatesInformative
import nl.rijksoverheid.mgo.component.theme.StatesPositive
import nl.rijksoverheid.mgo.feature.localisation.organizationList.OrganizationSearchCardState
import nl.rijksoverheid.mgo.feature.localisation.organizationList.R
import nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic.OrganizationListAutomaticSearchScreen
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

object OrganizationListManualCardTestTag {
  const val CARD = "OrganizationListManualCard"
}

/**
 * Composable that shows a card to show in [OrganizationListAutomaticSearchScreen].
 *
 * @param searchResult The [MgoOrganization] to display in this card.
 * @param cardState The [OrganizationSearchCardState] reflecting the state of this card.
 * @param onClick Called when the card has been clicked and the organization has not been added yet.
 * @param modifier The [Modifier] to be applied.
 */
@Composable
internal fun OrganizationListManualCard(
  searchResult: MgoOrganization,
  cardState: OrganizationSearchCardState,
  onClick: (searchResult: MgoOrganization) -> Unit,
  modifier: Modifier = Modifier,
) {
  val cardBackgroundColor =
    when (cardState) {
      OrganizationSearchCardState.ADD -> MaterialTheme.colorScheme.surface
      else ->
        MaterialTheme.colorScheme.background
          .copy(alpha = 0.5f)
          .compositeOver(MaterialTheme.colorScheme.surface)
    }
  MgoCard(modifier = modifier.testTag(OrganizationListManualCardTestTag.CARD)) {
    Row(
      modifier =
        Modifier
          .background(cardBackgroundColor)
          .clickable(enabled = cardState == OrganizationSearchCardState.ADD) {
            onClick(searchResult)
          }.padding(top = 12.dp, start = 12.dp, bottom = 12.dp),
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = searchResult.name,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold,
        )
        val address = searchResult.address
        if (address != null) {
          Text(
            modifier = Modifier.padding(top = 4.dp),
            text = address,
            style = MaterialTheme.typography.bodyMedium,
          )
        }
        when (cardState) {
          OrganizationSearchCardState.ADD -> {}
          OrganizationSearchCardState.ADDED -> {
            AdditionalText(
              text = CopyR.string.add_organization_already_added,
              icon = R.drawable.ic_search_result_card_added,
              contentColor = MaterialTheme.colorScheme.StatesPositive(),
              modifier = Modifier.padding(top = 8.dp),
            )
          }

          OrganizationSearchCardState.NOT_SUPPORTED -> {
            AdditionalText(
              text = CopyR.string.add_organization_not_participating,
              icon = R.drawable.ic_search_result_card_not_supported,
              contentColor = MaterialTheme.colorScheme.StatesInformative(),
              modifier = Modifier.padding(top = 8.dp),
            )
          }
        }
      }
      if (cardState == OrganizationSearchCardState.ADD) {
        IconButton(
          modifier = Modifier.align(Alignment.CenterVertically),
          onClick = { onClick(searchResult) },
        ) {
          Icon(
            painter = painterResource(id = R.drawable.ic_search_result_card_add),
            contentDescription = stringResource(id = CopyR.string.common_add).uppercase(),
            tint = MaterialTheme.colorScheme.primary,
          )
        }
      }
    }
  }
}

@Composable
private fun AdditionalText(
  @StringRes text: Int,
  @DrawableRes icon: Int,
  contentColor: Color,
  modifier: Modifier = Modifier,
) {
  CompositionLocalProvider(LocalContentColor provides contentColor) {
    Row(modifier = modifier) {
      Icon(
        painter = painterResource(id = icon),
        contentDescription = null,
      )
      Text(
        modifier = Modifier.padding(start = 6.dp),
        text = stringResource(text),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
      )
    }
  }
}

@PreviewLightDark
@Composable
internal fun OrganizationSearchCardAddPreview() {
  MgoTheme {
    OrganizationListManualCard(
      searchResult = TEST_MGO_ORGANIZATION,
      onClick = { },
      modifier = Modifier.padding(all = 16.dp),
      cardState = OrganizationSearchCardState.ADD,
    )
  }
}

@PreviewLightDark
@Composable
internal fun OrganizationSearchCardAddedPreview() {
  MgoTheme {
    OrganizationListManualCard(
      searchResult =
        TEST_MGO_ORGANIZATION
          .copy(added = true),
      onClick = { },
      modifier = Modifier.padding(all = 16.dp),
      cardState = OrganizationSearchCardState.ADDED,
    )
  }
}

@PreviewLightDark
@Composable
internal fun OrganizationSearchCardNotSupportedPreview() {
  MgoTheme {
    OrganizationListManualCard(
      searchResult =
        TEST_MGO_ORGANIZATION
          .copy(added = true),
      onClick = { },
      modifier = Modifier.padding(all = 16.dp),
      cardState = OrganizationSearchCardState.NOT_SUPPORTED,
    )
  }
}
