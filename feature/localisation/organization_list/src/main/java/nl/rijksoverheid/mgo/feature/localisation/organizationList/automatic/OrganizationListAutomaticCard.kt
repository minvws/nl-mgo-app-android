package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoCheckbox
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.StatesInformative
import nl.rijksoverheid.mgo.feature.localisation.organizationList.OrganizationSearchCardState
import nl.rijksoverheid.mgo.feature.localisation.organizationList.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun OrganizationListAutomaticCard(
  organization: MgoOrganization,
  cardState: OrganizationSearchCardState,
  onCheckedChange: (checked: Boolean) -> Unit,
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
  MgoCard(modifier = modifier) {
    Row(
      modifier =
        Modifier
          .background(cardBackgroundColor)
          .clickable(enabled = cardState != OrganizationSearchCardState.NOT_SUPPORTED) {
            val isChecked = cardState == OrganizationSearchCardState.ADDED
            onCheckedChange(!isChecked)
          }.padding(12.dp),
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = organization.name,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold,
        )
        val address = organization.address
        if (address != null) {
          Text(
            modifier = Modifier.padding(top = 4.dp),
            text = address,
            style = MaterialTheme.typography.bodyMedium,
          )
        }
        when (cardState) {
          OrganizationSearchCardState.ADD -> {}
          OrganizationSearchCardState.ADDED -> {}
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
      if (cardState != OrganizationSearchCardState.NOT_SUPPORTED) {
        MgoCheckbox(
          modifier = Modifier.align(Alignment.CenterVertically),
          checked = cardState == OrganizationSearchCardState.ADDED,
          onCheckedChange = onCheckedChange,
        )
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
internal fun OrganizationListAutomaticCardAddPreview() {
  MgoTheme {
    OrganizationListAutomaticCard(
      organization = TEST_MGO_ORGANIZATION,
      onCheckedChange = {},
      modifier = Modifier.padding(all = 16.dp),
      cardState = OrganizationSearchCardState.ADD,
    )
  }
}

@PreviewLightDark
@Composable
internal fun OrganizationListAutomaticCardAddedPreview() {
  MgoTheme {
    OrganizationListAutomaticCard(
      organization =
        TEST_MGO_ORGANIZATION
          .copy(added = true),
      onCheckedChange = {},
      modifier = Modifier.padding(all = 16.dp),
      cardState = OrganizationSearchCardState.ADDED,
    )
  }
}

@PreviewLightDark
@Composable
internal fun OrganizationAutomaticSearchCardNotSupportedPreview() {
  MgoTheme {
    OrganizationListAutomaticCard(
      organization =
        TEST_MGO_ORGANIZATION
          .copy(added = true),
      onCheckedChange = {},
      modifier = Modifier.padding(all = 16.dp),
      cardState = OrganizationSearchCardState.NOT_SUPPORTED,
    )
  }
}
